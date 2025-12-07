package life

import (
	"bytes"
	"errors"
	"io"
	"net/http"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.uber.org/mock/gomock"
)

func TestGetLifeSteps(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock data
	step := models.LifeStep{
		Name:        "Test Job",
		Type:        models.Job,
		Description: "Test description",
		InitialTime: 1000000,
		EndTime:     2000000,
		ProjectIDs:  []string{"proj1"},
		UserID:      "user1",
	}
	project := models.Project{
		Name: "Test Project",
	}

	// Mock user
	userID := bson.NewObjectID().Hex()
	user := &models.User{ID: &bson.ObjectID{}}
	user.ID.UnmarshalText([]byte(userID))

	tests := []struct {
		name           string
		stepType       string
		mockSteps      []models.LifeStep
		mockProjects   []models.Project
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful job retrieval",
			stepType:       "job",
			mockSteps:      []models.LifeStep{step},
			mockProjects:   []models.Project{project},
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `[{"id":"000000000000000000000000","name":"Test Job","type":"JOB","description":"Test description","initialTime":1000000,"endTime":2000000,"userId":"user1","projects":[{"id":"000000000000000000000000","name":"Test Project","startTime":"0001-01-01T00:00:00Z","endTime":"0001-01-01T00:00:00Z","company":"","userId":"","resources":null}]}]`,
		},
		{
			name:           "invalid step type",
			stepType:       "invalid",
			mockSteps:      nil,
			mockProjects:   nil,
			mockErr:        nil,
			expectedStatus: 400,
			expectedBody:   `{"error":"invalid step type"}`,
		},
		{
			name:           "database error",
			stepType:       "job",
			mockSteps:      nil,
			mockProjects:   nil,
			mockErr:        errors.New("db error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"db error"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMocklifeDataSource(ctrl)
			if tt.stepType == "job" {
				mockDS.EXPECT().GetLifeStepsByType(models.Job, userID).Return(tt.mockSteps, tt.mockErr)
				if tt.mockErr == nil && tt.mockSteps != nil {
					mockDS.EXPECT().GetProjects([]string{"proj1"}).Return(tt.mockProjects, nil)
				}
			}

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Params = gin.Params{{Key: "stepType", Value: tt.stepType}}
			c.Set("user", user)

			getLifeSteps(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}

func TestPostLifeStep(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock user
	userID := bson.NewObjectID().Hex()
	user := &models.User{ID: &bson.ObjectID{}}
	user.ID.UnmarshalText([]byte(userID))

	tests := []struct {
		name           string
		requestBody    string
		mockPostID     string
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful post",
			requestBody:    `{"name":"Test Step","type":"JOB","initialTime":1000000,"endTime":2000000}`,
			mockPostID:     "newid",
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"id":"newid"}`,
		},
		{
			name:           "invalid JSON",
			requestBody:    `invalid json`,
			mockPostID:     "",
			mockErr:        nil,
			expectedStatus: 400,
			expectedBody:   `{"error":"invalid character 'i' looking for beginning of value"}`,
		},
		{
			name:           "database error",
			requestBody:    `{"name":"Test","type":"JOB","initialTime":1000000,"endTime":2000000}`,
			mockPostID:     "",
			mockErr:        errors.New("db error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"db error"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMocklifeDataSource(ctrl)
			if tt.expectedStatus == 200 || tt.expectedStatus == 500 {
				mockDS.EXPECT().PostLifeStep(gomock.Any()).Return(tt.mockPostID, tt.mockErr)
			}

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Request = &http.Request{
				Method: "POST",
				Body:   io.NopCloser(bytes.NewBufferString(tt.requestBody)),
			}
			c.Set("user", user)

			postLifeStep(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
