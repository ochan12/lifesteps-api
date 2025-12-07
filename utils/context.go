package utils

import (
	"github.com/ochan12/lifesteps-api/models"

	"github.com/gin-gonic/gin"
)

// GetUserIDFromContext extracts the logged‑in user's ID from the Gin context.
// It returns the hex string of the ObjectID and a boolean indicating success.
func GetUserIDFromContext(c *gin.Context) (string, bool) {
	u, exists := c.Get("user")
	if !exists {
		return "", false
	}
	user, ok := u.(*models.User)
	if !ok || user.ID.Hex() == "" {
		return "", false
	}
	return user.ID.Hex(), true
}
