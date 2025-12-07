package resources

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

func TestGetResources(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock resources
	resources := []models.Resource{
		{ID: "1", Logo: "logo1", Name: "Resource1", URL: "url1"},
		{ID: "2", Logo: "logo2", Name: "Resource2", URL: "url2"},
	}

	tests := []struct {
		name           string
		query          string
		expectedIds    []string
		mockResources  []models.Resource
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful retrieval",
			query:          "ids=1,2",
			expectedIds:    []string{"1", "2"},
			mockResources:  resources,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `[{"id":"1","logo":"logo1","name":"Resource1","url":"url1"},{"id":"2","logo":"logo2","name":"Resource2","url":"url2"}]`,
		},
		{
			name:           "missing ids parameter",
			query:          "",
			expectedIds:    nil,
			mockResources:  nil,
			mockErr:        nil,
			expectedStatus: 400,
			expectedBody:   `{"error":"ids query parameter missing"}`,
		},
		{
			name:           "database error",
			query:          "ids=1",
			expectedIds:    []string{"1"},
			mockResources:  nil,
			mockErr:        errors.New("database error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"database error"}`,
		},
		{
			name:           "empty resources",
			query:          "ids=3",
			expectedIds:    []string{"3"},
			mockResources:  []models.Resource{},
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `[]`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMockresourceDataSource(ctrl)
			if tt.expectedIds != nil {
				mockDS.EXPECT().GetResources(tt.expectedIds).Return(tt.mockResources, tt.mockErr).AnyTimes()
			}

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Request = &http.Request{URL: &url.URL{RawQuery: tt.query}}

			getResources(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
