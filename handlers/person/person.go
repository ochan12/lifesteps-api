//go:generate mockgen -package person -source=person.go -destination=person_mock_test.go -typed true

package person

import (
	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"github.com/ochan12/lifesteps-api/utils"
)

type personDataSource interface {
	GetPersonData(userID string) (*models.Person, error)
}

// getPerson returns personal data for the user.
func getPerson(c *gin.Context, ds personDataSource) {
	userID, ok := utils.GetUserIDFromContext(c)
	if !ok {
		c.JSON(401, gin.H{"error": "unauthenticated"})
		return
	}
	person, err := ds.GetPersonData(userID)
	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	if person == nil {
		c.JSON(404, gin.H{"error": "person not found"})
		return
	}
	c.JSON(200, person)
}

// RegisterRoutes registers life‑related routes on the given group.
func RegisterRoutes(g *gin.Engine, ds personDataSource) {
	g.GET("/person", func(c *gin.Context) { getPerson(c, ds) })
}
