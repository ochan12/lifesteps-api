package auth

import (
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/config"
	"github.com/ochan12/lifesteps-api/datasource"
)

func hashPassword(password, salt string) string {
	h := sha256.New()
	h.Write([]byte(password + salt))
	return hex.EncodeToString(h.Sum(nil))
}

func RegisterAuthHandler(r *gin.Engine, dataSource datasource.DataSource, cfg config.Config) {
	// Basic Auth middleware
	r.Use(func(c *gin.Context) {
		auth := c.GetHeader("Authorization")
		if auth == "" {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "missing auth header"})
			return
		}
		const prefix = "Basic "
		if !strings.HasPrefix(auth, prefix) {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth scheme"})
			return
		}
		payload, err := base64.StdEncoding.DecodeString(auth[len(prefix):])
		if err != nil {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth payload"})
			return
		}
		parts := strings.SplitN(string(payload), ":", 2)
		if len(parts) != 2 {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid auth format"})
			return
		}

		username, password := parts[0], parts[1]
		user, err := dataSource.GetUser(username, hashPassword(password, cfg.AuthSalt))
		if err != nil || user == nil {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "invalid credentials"})
			return
		}
		c.Set("user", user)
		c.Next()
	})
}
