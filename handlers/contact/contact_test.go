package contact

import (
	"errors"
	"net/http/httptest"
	"strings"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/ochan12/lifesteps-api/models"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.uber.org/mock/gomock"
)

func TestGetContactField(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock user
	userID := bson.NewObjectID().Hex()
	user := &models.User{ID: &bson.ObjectID{}}
	user.ID.UnmarshalText([]byte(userID))

	// Mock contact data
	contact := &models.Contact{
		Email:      "test@example.com",
		Repository: "https://github.com/test",
		LinkedIn:   "https://linkedin.com/in/test",
		Phone:      "123-456-7890",
	}

	tests := []struct {
		name           string
		field          string
		mockContact    *models.Contact
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful email retrieval",
			field:          "email",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"email":"test@example.com"}`,
		},
		{
			name:           "successful repository retrieval",
			field:          "repository",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"repository":"https://github.com/test"}`,
		},
		{
			name:           "successful linkedIn retrieval",
			field:          "linkedIn",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"linkedIn":"https://linkedin.com/in/test"}`,
		},
		{
			name:           "successful phone retrieval",
			field:          "phone",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"phone":"123-456-7890"}`,
		},
		{
			name:           "contact not found",
			field:          "email",
			mockContact:    nil,
			mockErr:        nil,
			expectedStatus: 404,
			expectedBody:   `{"error":"contact not found"}`,
		},
		{
			name:           "database error",
			field:          "email",
			mockContact:    nil,
			mockErr:        errors.New("database error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"database error"}`,
		},
		{
			name:           "unknown field",
			field:          "unknown",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 400,
			expectedBody:   `{"error":"unknown field"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMockcontactDataSource(ctrl)
			mockDS.EXPECT().GetContactData(userID).Return(tt.mockContact, tt.mockErr).AnyTimes()

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Set("user", user)

			getContactField(c, mockDS, tt.field)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}

func TestGetContact(t *testing.T) {
	gin.SetMode(gin.TestMode)

	// Mock user
	userID := bson.NewObjectID().Hex()
	user := &models.User{ID: &bson.ObjectID{}}
	user.ID.UnmarshalText([]byte(userID))

	// Mock contact data
	contact := &models.Contact{
		Email:      "test@example.com",
		Repository: "https://github.com/test",
		LinkedIn:   "https://linkedin.com/in/test",
		Phone:      "123-456-7890",
	}

	tests := []struct {
		name           string
		mockContact    *models.Contact
		mockErr        error
		expectedStatus int
		expectedBody   string
	}{
		{
			name:           "successful contact retrieval",
			mockContact:    contact,
			mockErr:        nil,
			expectedStatus: 200,
			expectedBody:   `{"email":"test@example.com","repository":"https://github.com/test","linkedIn":"https://linkedin.com/in/test","phone":"123-456-7890"}`,
		},
		{
			name:           "contact not found",
			mockContact:    nil,
			mockErr:        nil,
			expectedStatus: 404,
			expectedBody:   `{"error":"contact not found"}`,
		},
		{
			name:           "database error",
			mockContact:    nil,
			mockErr:        errors.New("database error"),
			expectedStatus: 500,
			expectedBody:   `{"error":"database error"}`,
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			ctrl := gomock.NewController(t)
			defer ctrl.Finish()

			mockDS := NewMockcontactDataSource(ctrl)
			mockDS.EXPECT().GetContactData(userID).Return(tt.mockContact, tt.mockErr).AnyTimes()

			w := httptest.NewRecorder()
			c, _ := gin.CreateTestContext(w)
			c.Set("user", user)

			getContact(c, mockDS)

			if w.Code != tt.expectedStatus {
				t.Errorf("Expected status %d, got %d", tt.expectedStatus, w.Code)
			}

			if strings.TrimSpace(w.Body.String()) != tt.expectedBody {
				t.Errorf("Expected body %q, got %q", tt.expectedBody, strings.TrimSpace(w.Body.String()))
			}
		})
	}
}
