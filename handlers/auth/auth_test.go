package auth

import (
	"encoding/base64"
	"errors"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/config"
	"github.com/ochan12/lifesteps-api/models"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.uber.org/mock/gomock"
)

func TestRegisterAuthHandler(t *testing.T) {
	gin.SetMode(gin.TestMode)

	ctrl := gomock.NewController(t)
	defer ctrl.Finish()

	// Mock config
	cfg := config.Config{
		AuthSalt: "testsalt",
	}

	// Mock user
	userID := bson.NewObjectID()
	user := &models.User{
		ID:       &userID,
		Username: "testuser",
		Password: "hashedpass", // In real, it's hashed, but for mock, we check the input
		Email:    "test@example.com",
	}

	// Create mock data source
	ds := NewMockauthDataSource(ctrl)

	// Create Gin engine
	r := gin.New()
	RegisterAuthHandler(r, ds, cfg)

	// Add a test route
	r.GET("/test", func(c *gin.Context) {
		c.JSON(200, gin.H{"message": "success"})
	})

	tests := []struct {
		name           string
		authHeader     string
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "missing auth header",
			authHeader:     "",
			expectedStatus: 401,
			expectedBody:   `{"error":"missing auth header"}`,
		},
		{
			name:           "invalid auth scheme",
			authHeader:     "Bearer token",
			expectedStatus: 401,
			expectedBody:   `{"error":"invalid auth scheme"}`,
		},
		{
			name:           "invalid base64",
			authHeader:     "Basic invalidbase64",
			expectedStatus: 401,
			expectedBody:   `{"error":"invalid auth payload"}`,
		},
		{
			name:           "invalid format",
			authHeader:     "Basic " + base64.StdEncoding.EncodeToString([]byte("username")),
			expectedStatus: 401,
			expectedBody:   `{"error":"invalid auth format"}`,
		},
		{
			name:           "invalid credentials",
			authHeader:     "Basic " + base64.StdEncoding.EncodeToString([]byte("wronguser:wrongpass")),
			expectedStatus: 401,
			expectedBody:   `{"error":"invalid credentials"}`,
		},
		{
			name:           "database error",
			authHeader:     "Basic " + base64.StdEncoding.EncodeToString([]byte("testuser:pass")),
			expectedStatus: 401,
			expectedBody:   `{"error":"invalid credentials"}`,
		},
		{
			name:           "successful auth",
			authHeader:     "Basic " + base64.StdEncoding.EncodeToString([]byte("testuser:pass")),
			expectedStatus: 200,
			expectedBody:   `{"message":"success"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			// Set mock expectations
			switch tt.name {
			case "successful auth":
				ds.EXPECT().GetUser("testuser", hashPassword("pass", "testsalt")).Return(user, nil)
			case "database error":
				ds.EXPECT().GetUser("testuser", hashPassword("pass", "testsalt")).Return(nil, errors.New("db error"))
			case "invalid credentials":
				ds.EXPECT().GetUser("wronguser", gomock.Any()).Return(nil, nil)
				// For other cases, no GetUser call expected
			}

			// Create HTTP request
			req, _ := http.NewRequest("GET", "/test", nil)
			if tt.authHeader != "" {
				req.Header.Set("Authorization", tt.authHeader)
			}
			w := httptest.NewRecorder()

			// Serve the request
			r.ServeHTTP(w, req)

			// Assert status code
			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			// Assert response body
			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
