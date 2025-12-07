//go:generate mockgen -package auth -source=auth.go -destination=auth_mock_test.go -typed true

package auth

import (
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"log"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/config"
	"github.com/ochan12/lifesteps-api/models"
)

func hashPassword(password, salt string) string {
	h := sha256.New()
	h.Write([]byte(password + salt))
	return hex.EncodeToString(h.Sum(nil))
}

type authDataSource interface {
	GetUser(username string, password string) (*models.User, error)
}

func RegisterAuthHandler(r *gin.Engine, dataSource authDataSource, cfg config.Config) {
	r.Use(func(c *gin.Context) {
		auth := c.GetHeader("Authorization")
		if auth == "" {
			log.Printf("Auth failed: missing Authorization header for %s %s", c.Request.Method, c.Request.URL.Path)
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "missing auth header"})
			return
		}
		const prefix = "Basic "
		if !strings.HasPrefix(auth, prefix) {
			log.Printf("Auth failed: invalid auth scheme (expected 'Basic ') for %s %s", c.Request.Method, c.Request.URL.Path)
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth scheme"})
			return
		}
		payload, err := base64.StdEncoding.DecodeString(auth[len(prefix):])
		if err != nil {
			log.Printf("Auth failed: invalid base64 payload for %s %s: %v", c.Request.Method, c.Request.URL.Path, err)
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth payload"})
			return
		}
		parts := strings.SplitN(string(payload), ":", 2)
		if len(parts) != 2 {
			log.Printf("Auth failed: invalid auth format (expected username:password) for %s %s", c.Request.Method, c.Request.URL.Path)
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth format"})
			return
		}

		username, password := parts[0], parts[1]
		user, err := dataSource.GetUser(username, hashPassword(password, cfg.AuthSalt))
		if err != nil || user == nil {
			if err != nil {
				log.Printf("Auth failed: database error for user '%s' on %s %s: %v", username, c.Request.Method, c.Request.URL.Path, err)
			} else {
				log.Printf("Auth failed: invalid credentials for user '%s' on %s %s", username, c.Request.Method, c.Request.URL.Path)
			}
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid credentials"})
			return
		}
		log.Printf("Auth successful: user '%s' authenticated for %s %s", username, c.Request.Method, c.Request.URL.Path)
		c.Set("user", user)
		c.Next()
	})
}
