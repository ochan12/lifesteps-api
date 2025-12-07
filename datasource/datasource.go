package datasource

import (
	"context"
	"fmt"

	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"

	"github.com/ochan12/lifesteps-api/models"
)

// DataSource abstracts all MongoDB interactions used by the API.
type DataSource interface {
	GetLifeSteps(userID string) ([]models.LifeStep, error)
	GetLifeStepsByType(stepType models.StepType, userID string) ([]models.LifeStep, error)
	PostLifeStep(step models.LifeStep) (string, error)
	GetContactData(userID string) (*models.Contact, error)
	GetPersonData(userID string) (*models.Person, error)
	GetProjects(ids []string) ([]models.Project, error)
	GetResources(ids []string) ([]models.Resource, error)
	GetUser(username, token string) (*models.User, error)
	CreatePerson(ctx context.Context, person *models.Person) (string, error)
	CreateProject(ctx context.Context, project *models.Project) (string, error)
	CreateResource(ctx context.Context, resource *models.Resource) (string, error)
}

// MongoDataSource implements DataSource for a MongoDB instance.
type MongoDataSource struct {
	db            *mongo.Database
	lifeStepsColl *mongo.Collection
	projectsColl  *mongo.Collection
	resourcesColl *mongo.Collection
	personsColl   *mongo.Collection
	usersColl     *mongo.Collection
}

// NewMongoDataSource builds a new MongoDataSource with the default collections.
func NewMongoDataSource(db *mongo.Database) *MongoDataSource {
	return &MongoDataSource{
		db:            db,
		lifeStepsColl: db.Collection("lifeSteps"),
		projectsColl:  db.Collection("projects"),
		resourcesColl: db.Collection("resources"),
		personsColl:   db.Collection("people"),
		usersColl:     db.Collection("users"),
	}
}

// GetLifeSteps retrieves all life steps for the given user.
func (m *MongoDataSource) GetLifeSteps(userID string) ([]models.LifeStep, error) {
	filter := bson.M{"userId": userID}
	cursor, err := m.lifeStepsColl.Find(context.Background(), filter)
	if err != nil {
		return nil, err
	}
	defer cursor.Close(context.Background())

	var steps []models.LifeStep
	if err := cursor.All(context.Background(), &steps); err != nil {
		return nil, err
	}
	return steps, nil
}

func (m *MongoDataSource) GetLifeStepsByType(stepType models.StepType, userID string) ([]models.LifeStep, error) {
	filter := bson.M{"userId": userID, "type": stepType}
	cursor, err := m.lifeStepsColl.Find(context.Background(), filter)
	if err != nil {
		return nil, err
	}
	defer cursor.Close(context.Background())

	var steps []models.LifeStep
	if err := cursor.All(context.Background(), &steps); err != nil {
		return nil, err
	}
	return steps, nil
}

func (m *MongoDataSource) PostLifeStep(step models.LifeStep) (string, error) {
	if !step.Validate() {
		return "", mongo.ErrInvalidIndexValue
	}
	res, err := m.lifeStepsColl.InsertOne(context.Background(), step)
	if err != nil {
		return "", err
	}
	return res.InsertedID.(bson.ObjectID).Hex(), nil
}

func (m *MongoDataSource) GetContactData(userID string) (*models.Contact, error) {
	filter := bson.M{"userId": userID}
	var person models.Person
	err := m.personsColl.FindOne(context.Background(), filter).Decode(&person)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			return nil, nil
		}
		return nil, err
	}
	return person.Contact, nil
}

func (m *MongoDataSource) GetPersonData(userID string) (*models.Person, error) {
	filter := bson.M{"userId": userID}
	var person models.Person
	err := m.personsColl.FindOne(context.Background(), filter).Decode(&person)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			return nil, nil
		}
		return nil, err
	}
	return &person, nil
}

func (m *MongoDataSource) GetProjects(ids []string) ([]models.Project, error) {
	if len(ids) == 0 {
		return []models.Project{}, nil
	}
	objectIds := make([]bson.ObjectID, len(ids))
	for i, id := range ids {
		objectIds[i], _ = bson.ObjectIDFromHex(id)
	}
	filter := bson.M{"_id": bson.M{"$in": objectIds}}
	cursor, err := m.projectsColl.Find(context.Background(), filter)
	if err != nil {
		return nil, err
	}
	defer cursor.Close(context.Background())

	var projects []models.Project
	if err := cursor.All(context.Background(), &projects); err != nil {
		return nil, err
	}
	return projects, nil
}

func (m *MongoDataSource) GetResources(ids []string) ([]models.Resource, error) {
	if len(ids) == 0 {
		return []models.Resource{}, nil
	}
	filter := bson.M{"_id": bson.M{"$in": ids}}
	cursor, err := m.resourcesColl.Find(context.Background(), filter)
	if err != nil {
		return nil, err
	}
	defer cursor.Close(context.Background())

	var resources []models.Resource
	if err := cursor.All(context.Background(), &resources); err != nil {
		return nil, err
	}
	return resources, nil
}

func (m *MongoDataSource) GetUser(username, token string) (*models.User, error) {
	filter := bson.M{"username": username, "password": token}
	var user models.User
	err := m.usersColl.FindOne(context.Background(), filter).Decode(&user)
	if err != nil {
		if err == mongo.ErrNoDocuments {
			return nil, nil
		}
		return nil, err
	}
	return &user, nil
}

func (m *MongoDataSource) CreatePerson(ctx context.Context, person *models.Person) (string, error) {
	res, err := m.personsColl.InsertOne(ctx, person)
	if err != nil {
		return "", err
	}
	return res.InsertedID.(bson.ObjectID).Hex(), nil
}

func (m *MongoDataSource) CreateProject(ctx context.Context, project *models.Project) (string, error) {
	res, err := m.projectsColl.InsertOne(ctx, project)
	if err != nil {
		return "", err
	}
	return res.InsertedID.(bson.ObjectID).Hex(), nil
}

func (m *MongoDataSource) CreateResource(ctx context.Context, resource *models.Resource) (string, error) {
	_, err := m.resourcesColl.InsertOne(ctx, resource)
	if err != nil {
		return "", fmt.Errorf("error inserting resources %v", err)
	}
	// Resource uses string ID, so return the resource's ID
	return resource.ID, nil
}

func (m *MongoDataSource) CreateUser(ctx context.Context, user *models.User) (string, error) {
	res, err := m.usersColl.InsertOne(ctx, user)
	if err != nil {
		return "", err
	}
	return res.InsertedID.(bson.ObjectID).Hex(), nil
}
