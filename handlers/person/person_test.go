package person

import (
	"errors"
	"net/http/httptest"
	"strings"
	"testing"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.uber.org/mock/gomock"
)

func TestGetPerson(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock user
	userID := bson.NewObjectID().Hex()
	user := &models.User{ID: &bson.ObjectID{}}
	user.ID.UnmarshalText([]byte(userID))

	// Mock person
	person := &models.Person{
		FirstName: "John",
		LastName:  "Doe",
		BirthDate: time.Date(1990, 1, 1, 0, 0, 0, 0, time.UTC),
		Contact: &models.Contact{
			Email:      "john@example.com",
			Repository: "https://github.com/john",
			LinkedIn:   "https://linkedin.com/in/john",
			Phone:      "123-456-7890",
		},
	}

	tests := []struct {
		name           string
		mockPerson     *models.Person
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful person retrieval",
			mockPerson:     person,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"id":"000000000000000000000000","userId":"","name":"John","surname":"Doe","birthDate":"1990-01-01T00:00:00Z","contact":{"email":"john@example.com","repository":"https://github.com/john","linkedIn":"https://linkedin.com/in/john","phone":"123-456-7890"}}`,
		},
		{
			name:           "person not found",
			mockPerson:     nil,
			mockErr:        nil,
			expectedStatus: 404,
			expectedBody:   `{"error":"person not found"}`,
		},
		{
			name:           "database error",
			mockPerson:     nil,
			mockErr:        errors.New("database error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"database error"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMockpersonDataSource(ctrl)
			mockDS.EXPECT().GetPersonData(userID).Return(tt.mockPerson, tt.mockErr).AnyTimes()

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Set("user", user)

			getPerson(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
