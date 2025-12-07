package life

import (
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"

	"github.com/ochan12/lifesteps-api/models"
	"github.com/ochan12/lifesteps-api/utils"
)

type lifeDataSource interface {
	GetLifeStepsByType(stepType models.StepType, userID string) ([]models.LifeStep, error)
	PostLifeStep(step models.LifeStep) (string, error)
	GetProjects(ids []string) ([]models.Project, error)
}

// getLifeSteps handles GET /life/:stepType
func getLifeSteps(c *gin.Context, ds lifeDataSource) {
	stepTypeParam := c.Param("stepType")
	var stepType models.StepType
	switch strings.ToLower(stepTypeParam) {
	case "jobs", "job":
		stepType = models.Job
	case "education":
		stepType = models.Education
	case "trips", "trip":
		stepType = models.Travel
	case "hobbies", "hobby":
		stepType = models.Hobby
	default:
		c.JSON(http.StatusBadRequest, gin.H{"error": "invalid step type"})
		return
	}

	userID, ok := utils.GetUserIDFromContext(c)
	if !ok {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "unauthenticated"})
		return
	}

	steps, err := ds.GetLifeStepsByType(stepType, userID)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	stepsWithProject := make([]models.LifeStepWithProjects, len(steps))
	for i, step := range steps {
		projects, err := ds.GetProjects(step.ProjectIDs)
		if err != nil {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
			return
		}
		stepsWithProject[i] = models.LifeStepWithProjects{
			Projects: projects,
			LifeStep: step,
		}
	}
	c.JSON(http.StatusOK, stepsWithProject)
}

// postLifeStep handles POST /life/step
func postLifeStep(c *gin.Context, ds lifeDataSource) {
	var step models.LifeStep
	if err := c.ShouldBindJSON(&step); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	userID, ok := utils.GetUserIDFromContext(c)
	if !ok {
		c.JSON(http.StatusUnauthorized, gin.H{"error": "unauthenticated"})
		return
	}

	step.UserID = userID

	id, err := ds.PostLifeStep(step)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"id": id})
}

// RegisterRoutes registers life‑related routes on the given group.
func RegisterRoutes(g *gin.Engine, ds lifeDataSource) {
	g.GET("/life/:stepType", func(c *gin.Context) { getLifeSteps(c, ds) })
	g.POST("/life/step", func(c *gin.Context) { postLifeStep(c, ds) })
}
