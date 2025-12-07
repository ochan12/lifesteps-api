package project

import (
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
)

type projectDataSource interface {
	GetProjects(ids []string) ([]models.Project, error)
}

// getProjects handles GET /project?ids=...
func getProjects(c *gin.Context, ds projectDataSource) {
	idsParam := c.Query("ids")
	if idsParam == "" {
		c.JSON(400, gin.H{"error": "ids query parameter missing"})
		return
	}
	parts := strings.Split(idsParam, ",")
	var ids []string
	for _, p := range parts {
		ids = append(ids, p)
	}

	projs, err := ds.GetProjects(ids)
	if err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	c.JSON(200, projs)
}

// RegisterRoutes registers life‑related routes on the given group.
func RegisterRoutes(g *gin.Engine, ds projectDataSource) {
	g.GET("/project", func(c *gin.Context) { getProjects(c, ds) })
}
