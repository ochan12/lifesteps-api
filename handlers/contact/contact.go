package contact

import (
	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"github.com/ochan12/lifesteps-api/utils"
)

type contactDataSource interface {
	GetContactData(userID string) (*models.Contact, error)
}

// getContactField returns a single contact field: email, repository, linkedIn, or phone.
func getContactField(c *gin.Context, ds contactDataSource, field string) {
	userID, ok := utils.GetUserIDFromContext(c)
	if !ok {
		c.JSON(401, gin.H{"error": "unauthenticated"})
		return
	}
	contact, err := ds.GetContactData(userID)
	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	if contact == nil {
		c.JSON(404, gin.H{"error": "contact not found"})
		return
	}
	switch field {
	case "email":
		c.JSON(200, gin.H{"email": contact.Email})
	case "repository":
		c.JSON(200, gin.H{"repository": contact.Repository})
	case "linkedIn":
		c.JSON(200, gin.H{"linkedIn": contact.LinkedIn})
	case "phone":
		c.JSON(200, gin.H{"phone": contact.Phone})
	default:
		c.JSON(400, gin.H{"error": "unknown field"})
	}
}

// getContact returns the full contact document.
func getContact(c *gin.Context, ds contactDataSource) {
	userID, ok := utils.GetUserIDFromContext(c)
	if !ok {
		c.JSON(401, gin.H{"error": "unauthenticated"})
		return
	}
	contact, err := ds.GetContactData(userID)
	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	if contact == nil {
		c.JSON(404, gin.H{"error": "contact not found"})
		return
	}
	c.JSON(200, contact)
}

// RegisterRoutes registers life‑related routes on the given group.
func RegisterRoutes(g *gin.Engine, ds contactDataSource) {
	g.GET("/contact/email", func(c *gin.Context) { getContactField(c, ds, "email") })
	g.GET("/contact/repository", func(c *gin.Context) { getContactField(c, ds, "repository") })
	g.GET("/contact/linkedin", func(c *gin.Context) { getContactField(c, ds, "linkedIn") })
	g.GET("/contact/phone", func(c *gin.Context) { getContactField(c, ds, "phone") })
	g.GET("/contact", func(c *gin.Context) { getContact(c, ds) })
}
