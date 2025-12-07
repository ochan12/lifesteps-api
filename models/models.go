package models

import (
	"time"

	"go.mongodb.org/mongo-driver/v2/bson"
)

// StepType represents the type of life step.
type StepType string

const (
	Job       StepType = "JOB"
	Education StepType = "EDUCATION"
	Travel    StepType = "TRAVEL"
	Hobby     StepType = "HOBBY"
)

// Place represents a geographic location.
type Place struct {
	Name        string    `bson:"name" json:"name"`
	CountryCode string    `bson:"countryCode" json:"countryCode"`
	Position    *Position `bson:"position,omitempty" json:"position,omitempty"`
}

type Position struct {
	Latitude  float64 `bson:"lat" json:"lat"`
	Longitude float64 `bson:"lon" json:"lon"`
}

// LifeStep represents a user activity or milestone.
type LifeStep struct {
	ID          bson.ObjectID `bson:"_id,omitempty" json:"id"`
	Name        string        `bson:"name" json:"name"`
	Type        StepType      `bson:"type" json:"type"`
	Description string        `bson:"description,omitempty" json:"description,omitempty"`
	Photos      []string      `bson:"photos,omitempty" json:"photos,omitempty"`
	InitialTime int64         `bson:"initialTime" json:"initialTime"`
	EndTime     int64         `bson:"endTime" json:"endTime"`
	Place       *Place        `bson:"place,omitempty" json:"place,omitempty"`
	ProjectIDs  []string      `bson:"projects" json:"projects"`
	UserID      string        `bson:"userId" json:"userId"`
}

type LifeStepWithProjects struct {
	LifeStep
	Projects []Project `bson:"projects" json:"projects"`
}

// Validate checks that a LifeStep contains all required fields.
func (ls *LifeStep) Validate() bool {
	if ls.Name == "" || ls.Type == "" {
		return false
	}
	return true
}

// Person represents user personal data.
type Person struct {
	ID        bson.ObjectID `bson:"_id,omitempty" json:"id"`
	UserID    string        `bson:"userId" json:"userId"`
	FirstName string        `bson:"name" json:"name"`
	LastName  string        `bson:"surname" json:"surname"`
	BirthDate time.Time     `bson:"birthDate" json:"birthDate"`
	Contact   *Contact      `bson:"contact,omitempty" json:"contact,omitempty"`
}

// Contact holds contact information for a user.
type Contact struct {
	Email      string `bson:"email" json:"email"`
	Repository string `bson:"repository" json:"repository"`
	LinkedIn   string `bson:"linkedIn" json:"linkedIn"`
	Phone      string `bson:"phone" json:"phone"`
}

// Project represents a project associated with a life step.
type Project struct {
	ID          bson.ObjectID `bson:"_id,omitempty" json:"id"`
	Name        string        `bson:"name" json:"name"`
	Description string        `bson:"description,omitempty" json:"description,omitempty"`
	URL         string        `bson:"url,omitempty" json:"url,omitempty"`
	StartTime   time.Time     `bson:"startTime" json:"startTime"`
	EndTime     time.Time     `bson:"endTime" json:"endTime"`
	Company     string        `bson:"company" json:"company"`
	UserID      string        `bson:"userId" json:"userId"`
	Resources   []string      `bson:"resources" json:"resources"`
}

// Resource represents a resource linked to a project.
type Resource struct {
	ID   string `bson:"_id,omitempty" json:"id"`
	Logo string `bson:"logo" json:"logo"`
	Name string `bson:"name" json:"name"`
	URL  string `bson:"url" json:"url"`
}

// User represents authentication data.
type User struct {
	ID       *bson.ObjectID `bson:"_id,omitempty" json:"id"`
	Username string         `bson:"username" json:"username"`
	Password string         `bson:"password" json:"password"`
	Email    string         `bson:"email" json:"email"`
}
