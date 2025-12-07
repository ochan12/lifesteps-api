package project

import (
	"errors"
	"net/http"
	"net/http/httptest"
	"net/url"
	"strings"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"go.uber.org/mock/gomock"
)

func TestGetProjects(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock projects
	projects := []models.Project{
		{Name: "Project1", Description: "Desc1", URL: "url1", Company: "Company1"},
		{Name: "Project2", Description: "Desc2", URL: "url2", Company: "Company2"},
	}

	tests := []struct {
		name           string
		query          string
		expectedIds    []string
		mockProjects   []models.Project
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful retrieval",
			query:          "ids=1,2",
			expectedIds:    []string{"1", "2"},
			mockProjects:   projects,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `[{"id":"000000000000000000000000","name":"Project1","description":"Desc1","url":"url1","startTime":"0001-01-01T00:00:00Z","endTime":"0001-01-01T00:00:00Z","company":"Company1","userId":"","resources":null},{"id":"000000000000000000000000","name":"Project2","description":"Desc2","url":"url2","startTime":"0001-01-01T00:00:00Z","endTime":"0001-01-01T00:00:00Z","company":"Company2","userId":"","resources":null}]`,
		},
		{
			name:           "missing ids parameter",
			query:          "",
			expectedIds:    nil,
			mockProjects:   nil,
			mockErr:        nil,
			expectedStatus: 400,
			expectedBody:   `{"error":"ids query parameter missing"}`,
		},
		{
			name:           "database error",
			query:          "ids=1",
			expectedIds:    []string{"1"},
			mockProjects:   nil,
			mockErr:        errors.New("database error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"database error"}`,
		},
		{
			name:           "empty projects",
			query:          "ids=3",
			expectedIds:    []string{"3"},
			mockProjects:   []models.Project{},
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `[]`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMockprojectDataSource(ctrl)
			if tt.expectedIds != nil {
				mockDS.EXPECT().GetProjects(tt.expectedIds).Return(tt.mockProjects, tt.mockErr).AnyTimes()
			}

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Request = &http.Request{URL: &url.URL{RawQuery: tt.query}}

			getProjects(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
