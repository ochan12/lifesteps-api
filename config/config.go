package config

import (
	"fmt"
	"os"

	"github.com/joho/godotenv"
)

type Config struct {
	MongoURI   string
	DBName     string
	AuthSalt   string
	ServerPort string
	Password   string
}

func LoadConfig() (*Config, error) {
	_ = godotenv.Load()

	cfg := &Config{
		MongoURI:   os.Getenv("MONGO_URI"),
		DBName:     os.Getenv("DB_NAME"),
		AuthSalt:   os.Getenv("AUTH_SALT"),
		ServerPort: os.Getenv("SERVER_PORT"),
		Password:   os.Getenv("PASSWORD"),
	}

	if cfg.MongoURI == "" {
		return nil, fmt.Errorf("MONGO_URI is required")
	}
	if cfg.DBName == "" {
		cfg.DBName = "lifesteps"
	}
	if cfg.AuthSalt == "" {
		cfg.AuthSalt = "default_salt"
	}
	if cfg.ServerPort == "" {
		cfg.ServerPort = "8080"
	}
	return cfg, nil
}
