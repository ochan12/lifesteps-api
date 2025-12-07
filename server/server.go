package server

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/datasource"
)

func CreateServer(ds datasource.DataSource) *gin.Engine {
	r := gin.Default()

	r.GET("/", func(c *gin.Context) {
		c.String(http.StatusOK, "Hello there, this is Mateo's backend!")
	})
	return r
}
