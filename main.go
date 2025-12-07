package main

import (
	"context"
	"fmt"
	"log"

	"github.com/ochan12/lifesteps-api/config"
	"github.com/ochan12/lifesteps-api/datasource"
	"github.com/ochan12/lifesteps-api/handlers/auth"
	"github.com/ochan12/lifesteps-api/handlers/contact"
	"github.com/ochan12/lifesteps-api/handlers/life"
	"github.com/ochan12/lifesteps-api/handlers/person"
	"github.com/ochan12/lifesteps-api/handlers/project"
	"github.com/ochan12/lifesteps-api/handlers/resources"
	"github.com/ochan12/lifesteps-api/server"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
)

func main() {
	cfg, err := config.LoadConfig()
	if err != nil {
		log.Fatalf("Failed to load config: %v", err)
	}

	clientOpts := options.Client().ApplyURI(cfg.MongoURI)
	mongoClient, err := mongo.Connect(clientOpts)
	if err != nil {
		log.Fatalf("Failed to connect to MongoDB: %v", err)
	}
	defer func() {
		if err := mongoClient.Disconnect(context.Background()); err != nil {
			log.Printf("Error disconnecting MongoDB: %v", err)
		}
	}()

	// Ping the MongoDB server to verify connectivity
	if err := mongoClient.Ping(context.Background(), nil); err != nil {
		log.Fatalf("Failed to ping MongoDB: %v", err)
	}

	db := mongoClient.Database(cfg.DBName)
	dataSource := datasource.NewMongoDataSource(db)
	server := server.CreateServer(dataSource)
	auth.RegisterAuthHandler(server, dataSource, *cfg)
	life.RegisterRoutes(server, dataSource)
	person.RegisterRoutes(server, dataSource)
	contact.RegisterRoutes(server, dataSource)
	project.RegisterRoutes(server, dataSource)
	resources.RegisterRoutes(server, dataSource)

	addr := fmt.Sprintf(":%s", cfg.ServerPort)
	log.Printf("Starting server on %s", addr)
	if err := server.Run(addr); err != nil {
		log.Fatalf("Server error: %v", err)
	}
}
