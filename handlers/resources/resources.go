package resources

import (
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
)

type resourceDataSource interface {
	GetResources(ids []string) ([]models.Resource, error)
}

// getResources handles GET /resources?ids=...
func getResources(c *gin.Context, ds resourceDataSource) {
	idsParam := c.Query("ids")
	if idsParam == "" {
		c.JSON(400, gin.H{"error": "ids query parameter missing"})
		return
	}
	parts := strings.Split(idsParam, ",")

	res, err := ds.GetResources(parts)
	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	c.JSON(200, res)
}

// RegisterRoutes registers life‑related routes on the given group.
func RegisterRoutes(g *gin.Engine, ds resourceDataSource) {
	g.GET("/resources", func(c *gin.Context) { getResources(c, ds) })
}
