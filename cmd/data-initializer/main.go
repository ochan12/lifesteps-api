package main

import (
	"context"
	"crypto/sha256"
	"encoding/hex"
	"log"
	"time"

	"github.com/ochan12/lifesteps-api/config"
	"github.com/ochan12/lifesteps-api/datasource"
	"github.com/ochan12/lifesteps-api/models"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
)

type DataInitializer struct {
	DataSource *datasource.MongoDataSource
}

func NewDataInitializer(ds *datasource.MongoDataSource) *DataInitializer {
	return &DataInitializer{
		DataSource: ds,
	}
}

func (di *DataInitializer) getPlaces() map[string]*models.Place {
	return map[string]*models.Place{
		"cordoba": {
			Name:        "Córdoba",
			CountryCode: "ARG",
			Position: &models.Position{
				Latitude:  -31.4135,  // (-31.4135).toLong()
				Longitude: -64.18105, // (-64.18105).toLong()
			},
		},
		"sweden": {
			Name:        "Stockholm",
			CountryCode: "SWE",
			Position: &models.Position{
				Latitude:  59.33258, // (59.33258).toLong()
				Longitude: 18.0649,  // (18.0649).toLong()
			},
		},
		"italy": {
			Name:        "Siracuse",
			CountryCode: "ITA",
			Position: &models.Position{
				Latitude:  37.075474, // (37.075474).toLong()
				Longitude: 15.286586, // (15.286586).toLong()
			},
		},
		"mexico": {
			Name:        "Mexico",
			CountryCode: "MEX",
			Position: &models.Position{
				Latitude:  20.611313,  // (20.611313).toLong()
				Longitude: -87.084000, // (-87.084000).toLong()
			},
		},
		"spain": {
			Name:        "Spain",
			CountryCode: "ESP",
			Position: &models.Position{
				Latitude:  40.2085, // (40.2085).toLong()
				Longitude: -3.713,  // (-3.713).toLong()
			},
		},
		"france": {
			Name:        "France",
			CountryCode: "FRA",
			Position: &models.Position{
				Latitude:  46.2276,  // (46.2276).toLong()
				Longitude: 2.349014, // (2.349014).toLong()
			},
		},
		"belgium": {
			Name:        "Belgium",
			CountryCode: "BEL",
			Position: &models.Position{
				Latitude:  51.049999, // (51.049999).toLong()
				Longitude: 3.733333,  // (3.733333).toLong()
			},
		},
		"england": {
			Name:        "England",
			CountryCode: "GBR",
			Position: &models.Position{
				Latitude:  51.503399, // (51.503399).toLong()
				Longitude: -0.119519, // (-0.119519).toLong()
			},
		},
		"scotland": {
			Name:        "Scotland",
			CountryCode: "GBR",
			Position: &models.Position{
				Latitude:  55.948612, // (55.948612).toLong()
				Longitude: -3.200833, // (-3.200833).toLong()
			},
		},
		"germany": {
			Name:        "Germany",
			CountryCode: "DEU",
			Position: &models.Position{
				Latitude:  52.518898, // (52.518898).toLong()
				Longitude: 13.401797, // (13.401797).toLong()
			},
		},
		"czech": {
			Name:        "Czech Republic",
			CountryCode: "CZE",
			Position: &models.Position{
				Latitude:  50.073658, // (50.073658).toLong()
				Longitude: 14.418540, // (14.418540).toLong()
			},
		},
		"switzerland": {
			Name:        "Switzerland",
			CountryCode: "CHE",
			Position: &models.Position{
				Latitude:  47.451542, // (47.451542).toLong()
				Longitude: 8.564572,  // (8.564572).toLong()
			},
		},
		"portugal": {
			Name:        "Portugal",
			CountryCode: "PRT",
			Position: &models.Position{
				Latitude:  38.736946, // (38.736946).toLong()
				Longitude: -9.142685, // (-9.142685).toLong()
			},
		},
		"hungary": {
			Name:        "Hungary",
			CountryCode: "HUN",
			Position: &models.Position{
				Latitude:  47.497913, // (47.497913).toLong()
				Longitude: 19.040236, // (19.040236).toLong()
			},
		},
		"austria": {
			Name:        "Austria",
			CountryCode: "AUT",
			Position: &models.Position{
				Latitude:  48.210033, // (48.210033).toLong()
				Longitude: 16.363449, // (16.363449).toLong()
			},
		},
		"denmark": {
			Name:        "Denmark",
			CountryCode: "DNK",
			Position: &models.Position{
				Latitude:  55.620750, // (55.620750).toLong()
				Longitude: 12.650462, // (12.650462).toLong()
			},
		},
		"netherlands": {
			Name:        "Netherlands",
			CountryCode: "NLD",
			Position: &models.Position{
				Latitude:  52.308056, // (52.308056).toLong()
				Longitude: 4.764167,  // (4.764167).toLong()
			},
		},
		"colombia": {
			Name:        "Colombia",
			CountryCode: "COL",
			Position: &models.Position{
				Latitude:  4.624335,   // (4.624335).toLong()
				Longitude: -74.063644, // (-74.063644).toLong()
			},
		},
		"chile": {
			Name:        "Chile",
			CountryCode: "CHL",
			Position: &models.Position{
				Latitude:  -33.447487, // (-33.447487).toLong()
				Longitude: -70.673676, // (-70.673676).toLong()
			},
		},
		"brazil": {
			Name:        "Brazil",
			CountryCode: "BRA",
			Position: &models.Position{
				Latitude:  -15.793889, // (-15.793889).toLong()
				Longitude: -47.882778, // (-47.882778).toLong()
			},
		},
		"uruguay": {
			Name:        "Uruguay",
			CountryCode: "URY",
			Position: &models.Position{
				Latitude:  -34.90328, // (-34.90328).toLong()
				Longitude: -56.18816, // (-56.18816).toLong()
			},
		},
		"poland": {
			Name:        "Poland",
			CountryCode: "POL",
			Position: &models.Position{
				Latitude:  51.9194, // (51.9194).toLong()
				Longitude: 19.1451, // (19.1451).toLong()
			},
		},
		"norway": {
			Name:        "Norway",
			CountryCode: "NOR",
			Position: &models.Position{
				Latitude:  60.4720, // (60.4720).toLong()
				Longitude: 8.4689,  // (8.4689).toLong()
			},
		},
		"croatia": {
			Name:        "Croatia",
			CountryCode: "HRV",
			Position: &models.Position{
				Latitude:  45.8150, // (45.8150).toLong()
				Longitude: 15.9819, // (15.9819).toLong()
			},
		},
		"turkey": {
			Name:        "Turkey",
			CountryCode: "TUR",
			Position: &models.Position{
				Latitude:  39.9289, // (39.9289).toLong()
				Longitude: 32.8560, // (32.8560).toLong()
			},
		},
	}
}

func hashPassword(password, salt string) string {
	h := sha256.New()
	h.Write([]byte(password + salt))
	return hex.EncodeToString(h.Sum(nil))
}

func (di *DataInitializer) InitializeData() error {
	ctx := context.Background()
	places := di.getPlaces()

	// Create resources first
	resources := []models.Resource{
		{
			ID:   "typescript",
			Name: "TypeScript",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/4/4c/Typescript_logo_2020.svg",
			URL:  "https://www.typescriptlang.org/",
		},
		{
			ID:   "javascript",
			Name: "JavaScript",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/3/3b/Javascript_Logo.png",
			URL:  "https://www.javascript.com/",
		},
		{
			ID:   "node",
			Name: "Node.js",
			Logo: "/img/logos/logo_node.png",
			URL:  "https://nodejs.org/en/",
		},
		{
			ID:   "docker",
			Name: "Docker",
			Logo: "/img/logos/logo_docker.png",
			URL:  "https://www.docker.com/",
		},
		{
			ID:   "react",
			Name: "ReactJS",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg",
			URL:  "https://reactjs.org/",
		},
		{
			ID:   "aws",
			Name: "AWS",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/9/93/Amazon_Web_Services_Logo.svg",
			URL:  "https://aws.amazon.com/",
		},
		{
			ID:   "elastic",
			Name: "Elastic",
			Logo: "/img/logos/logo_elastic.png",
			URL:  "https://www.elastic.co/",
		},
		{
			ID:   "redis",
			Name: "Redis",
			Logo: "/img/logos/logo_redis.png",
			URL:  "https://redis.io/",
		},
		{
			ID:   "turborepo",
			Name: "Turborepo",
			Logo: "https://user-images.githubusercontent.com/4060187/106504110-82f58d00-6494-11eb-87b7-a16d4f68bc5a.png",
			URL:  "https://turborepo.org/",
		},
		{
			ID:   "netsuite",
			Name: "NetSuite",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/9/95/Oracle_NetSuite_2021.png",
			URL:  "https://www.netsuite.com/portal/home.shtml",
		},
		{
			ID:   "python",
			Name: "Python",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/c/c3/Python-logo-notext.svg",
			URL:  "https://www.python.org/",
		},
		{
			ID:   "firebase",
			Name: "Firebase",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/4/46/Touchicon-180.png",
			URL:  "https://firebase.google.com/",
		},
		{
			ID:   "java",
			Name: "Java",
			Logo: "/img/logos/logo_java.png",
			URL:  "https://www.java.com/en/",
		},
		{
			ID:   "express",
			Name: "Express",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/6/64/Expressjs.png",
			URL:  "https://expressjs.com/",
		},
		{
			ID:   "postgresql",
			Name: "PostgreSQL",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg",
			URL:  "https://www.postgresql.org/",
		},
		{
			ID:   "webpack",
			Name: "Webpack",
			Logo: "/img/logos/logo_webpack.png",
			URL:  "https://webpack.js.org/",
		},
		{
			ID:   "sass",
			Name: "SASS",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/9/96/Sass_Logo_Color.svg",
			URL:  "https://sass-lang.com/",
		},
		{
			ID:   "git",
			Name: "git",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/3/3f/Git_icon.svg",
			URL:  "https://git-scm.com/",
		},
		{
			ID:   "bitbucket",
			Name: "Bitbucket",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/0/0e/Bitbucket-blue-logomark-only.svg",
			URL:  "https://bitbucket.org/",
		},
		{
			ID:   "heroku",
			Name: "Heroku",
			Logo: "/img/logos/logo_heroku.svg",
			URL:  "https://heroku.com",
		},
		{
			ID:   "kotlin",
			Name: "Kotlin",
			Logo: "/img/logos/logo_kotlin.svg",
			URL:  "https://kotlinlang.org/",
		},
		{
			ID:   "android",
			Name: "Android",
			Logo: "/img/logos/logo_android.svg",
			URL:  "https://www.android.com/",
		},
		{
			ID:   "dagger",
			Name: "Dagger",
			Logo: "",
			URL:  "https://dagger.dev/",
		},
		{
			ID:   "redux",
			Name: "Redux",
			Logo: "/img/logos/logo_redux.svg",
			URL:  "https://redux.js.org/",
		},
		{
			ID:   "nginx",
			Name: "Nginx",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/c/c5/Nginx_logo.svg",
			URL:  "https://www.nginx.com/",
		},
		{
			ID:   "traefik",
			Name: "Traefik",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/1/1b/Traefik.logo.png",
			URL:  "https://traefik.io/",
		},
		{
			ID:   "fastify",
			Name: "Fastify",
			Logo: "/img/logos/logo_fastify.png",
			URL:  "https://www.fastify.io/",
		},
		{
			ID:   "gunicorn",
			Name: "Gunicorn",
			Logo: "/img/logos/logo_gunicorn.png",
			URL:  "https://gunicorn.org/",
		},
		{
			ID:   "mongodb",
			Name: "MongoDB",
			Logo: "/img/logos/logo_mongo.svg",
			URL:  "https://www.mongodb.com/",
		},
		{
			ID:   "nextjs",
			Name: "Next.js",
			Logo: "/img/logos/logo_nextjs.png",
			URL:  "https://nextjs.org/",
		},
		{
			ID:   "terraform",
			Name: "Terraform",
			Logo: "/img/logos/logo_terraform.png",
			URL:  "https://www.terraform.io/",
		},
		{
			ID:   "kubernetes",
			Name: "Kubernetes",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/3/39/Kubernetes_logo_without_workmark.svg",
			URL:  "https://kubernetes.io/",
		},
		{
			ID:   "kind",
			Name: "KIND",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/f/f6/KinD_logo.png",
			URL:  "https://kind.sigs.k8s.io/",
		},
		{
			ID:   "reactjs",
			Name: "ReactJS",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg",
			URL:  "https://reactjs.org/",
		},
		{
			ID:   "expressjs",
			Name: "Express",
			Logo: "https://upload.wikimedia.org/wikipedia/commons/6/64/Expressjs.png",
			URL:  "https://expressjs.com/",
		},
		{
			ID:   "golang",
			Name: "Go",
			Logo: "https://go.dev/images/go-logo-white.svg",
			URL:  "https://go.dev",
		},
	}

	// Insert resources
	projectIDs := make(map[string]string)
	for _, resource := range resources {
		id, err := di.DataSource.CreateResource(ctx, &resource)
		if err != nil {
			return err
		}
		log.Printf("Created resource: %s", resource.Name)
		projectIDs[resource.ID] = id
	}

	cfg, err := config.LoadConfig()
	if err != nil {
		log.Fatalf("Failed to load config: %v", err)
	}
	userID, err := di.DataSource.CreateUser(ctx, &models.User{
		Email:    "mateochando@gmail.com",
		Username: "riggoch",
		Password: hashPassword(cfg.Password, cfg.AuthSalt),
	})
	if err != nil {
		log.Fatalf("Failed to create user: %v", err)
	}

	// Create projects
	projects := []models.Project{
		// Qbit projects
		{
			Name:        "Argentinian Localisation",
			Description: "Extend Netsuite functionality for Argentina",
			Company:     "Qbit",
			UserID:      userID,
			Resources:   []string{"javascript", "netsuite"},
		},
		{
			Name:        "Tax Calculation",
			Description: "Implement tax-specific calculation and bulk processing of invoices",
			Company:     "Qbit",
			UserID:      userID,
			Resources:   []string{"javascript", "netsuite"},
		},
		// RD projects
		{
			Name:        "API Ingestion",
			Description: "Create applications to ingest data from Twitter, Facebook, YouTube, Instagram, Google News APIs in an automated way",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"python", "gunicorn"},
		},
		{
			Name:        "Convert JS to Python specific Apps",
			Description: "Replace JS scripts managed by PM2 processes to Python specific apps server-like",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"python", "typescript"},
		},
		{
			Name:        "Dockerization of stack",
			Description: "Replace monolithic app by microservice apps that can be deployed into a Docker Swarm, create images for each of them",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"docker", "traefik"},
		},
		{
			Name:        "Website for client creation",
			Description: "Develop a website to automate the creation of clients specifying users/keywords and launching/editing scrapers on the fly",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"react", "typescript"},
		},
		{
			Name:        "Management of ELK stack",
			Description: "In charge of manipulating multiple indexes for different clients, creating analysis graphics and implementing ML Alerts for peaks behaviours",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"elastic"},
		},
		{
			Name:        "Prediction of the 2019 Argentinian presidential election",
			Description: "Through the Social Media ingested data we created a model to analyse the behaviour of voters and predict the outcome of the election",
			Company:     "Reputación digital",
			UserID:      userID,
			Resources:   []string{"elastic"},
		},
		// Cruncho projects
		{
			Name:        "Event Manager",
			Description: "Implementation of an Event Manager to handle events and publish them into an event calendar",
			URL:         "https://cruncho.com/event-calendar/",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"aws", "mongodb", "react"},
		},
		{
			Name:        "Implement APIs Ingestion",
			Description: "Use Google / Foursquare / TripAdvisor and other APIs to feed the guides",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"express", "redis", "node", "mongodb"},
		},
		{
			Name:        "Guides Features Implementation",
			Description: "Implement features about content filtering, sorting and improving the quality",
			URL:         "https://stockholm.cruncho.co/",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"react", "redux", "typescript", "sass"},
		},
		{
			Name:        "Migrate Amplify Database",
			Description: "Move Amplify to own hosted database",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"aws", "mongodb", "traefik"},
		},
		{
			Name:        "Monorepo implementation",
			Description: "Migrate multi-repo structure to single monorepo and conditional builds pipeline",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"turborepo", "bitbucket", "docker", "webpack"},
		},
		{
			Name:        "Events API",
			Description: "Created an API to be consumed directly by our clients with token authorization and content filtering",
			URL:         "https://events-api.cruncho.co/swagger",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"redis", "mongodb", "fastify", "node", "docker", "typescript"},
		},
		{
			Name:        "Guides API",
			Description: "Developed features for internal consumption of our own guides. ",
			Company:     "Cruncho",
			UserID:      userID,
			Resources:   []string{"redis", "mongodb", "fastify", "elastic", "traefik", "node", "docker", "typescript", "postgresql"},
		},
		// Solo projects
		{
			Name:        "Lifestep API",
			Description: "Create a simple API to fetch data for my website portfolio",
			Company:     "Solo",
			UserID:      userID,
			Resources:   []string{"kotlin", "mongodb"},
			URL:         "https://lifesteps-api.herokuapp.com/",
		},
		{
			Name:        "My website",
			Description: "YOU'RE HERE!",
			Company:     "Solo",
			UserID:      userID,
			Resources:   []string{"reactjs", "nextjs", "sass", "typescript"},
			URL:         "https://riggoch.vercel.app/",
		},
		{
			Name:        "myLook",
			Description: "Thesis graduation project to promote small clothing stores reaching out to clients. This was a group project where I took mainly of Android features",
			Company:     "Solo",
			UserID:      userID,
			Resources:   []string{"android", "firebase"},
			URL:         "https://github.com/myLook2018/myLook",
		},
		{
			Name:        "TimeTracker",
			Description: "Android app to track periods of time",
			Company:     "Solo",
			UserID:      userID,
			Resources:   []string{"android", "firebase", "dagger", "kotlin"},
			URL:         "https://github.com/ochan12/TimeTracker",
		},
		// Tracab projects
		{
			Name:        "Match Simulator",
			Description: "Create an application to allow game streaming through internal tools",
			Company:     "Tracab",
			UserID:      userID,
			Resources:   []string{"node", "redis", "typescript"},
		},
		{
			Name:        "Backend tasks",
			Description: "Complete different tasks including bugs, features, endpoints for internal and external usage",
			Company:     "Tracab",
			UserID:      userID,
			Resources:   []string{"redis", "mongodb", "express", "javascript", "typescript", "react", "docker", "kubernetes", "bitbucket", "nginx", "kind", "terraform"},
		},
		{
			Name:        "Skeleton Ingestion System",
			Description: "Create a system for real-time data processing of skeleton data of players in football matches",
			Company:     "Tracab",
			UserID:      userID,
			Resources:   []string{"golang", "mongodb", "node", "redis", "docker", "kubernetes"},
		},
	}

	// Insert projects and collect IDs
	qbitProjectIDs := []string{}
	rdProjectIDs := []string{}
	crunchoProjectIDs := []string{}
	soloProjectIDs := []string{}
	tracabProjectIDs := []string{}

	for _, project := range projects {
		id, err := di.DataSource.CreateProject(ctx, &project)
		if err != nil {
			return err
		}
		log.Printf("Created project: %s", project.Name)

		switch project.Company {
		case "Qbit":
			qbitProjectIDs = append(qbitProjectIDs, id)
		case "Reputación digital":
			rdProjectIDs = append(rdProjectIDs, id)
		case "Cruncho":
			crunchoProjectIDs = append(crunchoProjectIDs, id)
		case "Solo":
			soloProjectIDs = append(soloProjectIDs, id)
		case "Tracab":
			tracabProjectIDs = append(tracabProjectIDs, id)
		}
	}

	// Create person
	person := &models.Person{
		UserID:    userID,
		FirstName: "Mateo",
		LastName:  "Ochandorena",
		BirthDate: time.Date(1996, 2, 29, 0, 0, 0, 0, time.UTC),
		Contact: &models.Contact{
			Email:      "mateochando@gmail.com",
			Repository: "https://github.com/ochan12",
			LinkedIn:   "https://www.linkedin.com/in/m-ochandorena/",
			Phone:      "+460767428890",
		},
	}

	_, err = di.DataSource.CreatePerson(ctx, person)
	if err != nil {
		return err
	}
	log.Printf("Created person: %s %s", person.FirstName, person.LastName)

	// Create life steps
	lifeSteps := []models.LifeStep{
		// Jobs
		{
			Name:        "Qbit",
			Type:        models.Job,
			Description: "Provide B2B services on an ERP suite",
			InitialTime: time.Date(2018, 4, 1, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2019, 3, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["cordoba"],
			ProjectIDs:  qbitProjectIDs,
			UserID:      userID,
			Photos:      []string{"/img/jobs/qbit.png"},
		},
		{
			Name:        "Reputación digital",
			Type:        models.Job,
			Description: "Gather data from different social media platforms to analyse the impact of social actors/companies",
			InitialTime: time.Date(2019, 3, 15, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2020, 3, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["cordoba"],
			ProjectIDs:  rdProjectIDs,
			UserID:      userID,
			Photos:      []string{"/img/jobs/rd.png"},
		},
		{
			Name:        "Cruncho",
			Type:        models.Job,
			Description: "Create city guides grouping data from different APIs",
			InitialTime: time.Date(2020, 9, 19, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2022, 10, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["sweden"],
			ProjectIDs:  crunchoProjectIDs,
			UserID:      userID,
			Photos:      []string{"/img/jobs/cruncho.png"},
		},
		{
			Name:        "Tracab",
			Type:        models.Job,
			Description: "Provide sports statistics for clients",
			InitialTime: time.Date(2022, 10, 2, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2025, 6, 30, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["sweden"],
			ProjectIDs:  tracabProjectIDs,
			UserID:      userID,
			Photos:      []string{"/img/jobs/tracab.png"},
		},
		{
			Name:        "Insurely",
			Type:        models.Job,
			Description: "Proper peace of mind",
			InitialTime: time.Date(2025, 8, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["sweden"],
			ProjectIDs:  []string{},
			UserID:      userID,
			Photos:      []string{"/img/jobs/insurely.svg"},
		},
		// Solo projects
		{
			Name:        "Solo projects",
			Type:        models.Hobby,
			Description: "Couple of projects just to have fun",
			InitialTime: time.Date(2019, 3, 15, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["cordoba"],
			ProjectIDs:  soloProjectIDs,
			UserID:      userID,
			Photos:      []string{"/img/jobs/solo.svg"},
		},
		// Travels
		{
			Name:        "Argentina",
			Type:        models.Travel,
			Description: "Home country, place of the wines and Fernet",
			InitialTime: time.Date(1996, 2, 29, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2020, 3, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["cordoba"],
			UserID:      userID,
		},
		{
			Name:        "Sweden",
			Type:        models.Travel,
			Description: "Currently living here",
			InitialTime: time.Date(2020, 9, 19, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["sweden"],
			UserID:      userID,
		},
		{
			Name:        "Italy",
			Type:        models.Travel,
			Description: "Got my citizenship over pizza and pasta",
			InitialTime: time.Date(2021, 4, 15, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2021, 9, 15, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["italy"],
			UserID:      userID,
		},
		{
			Name:        "México",
			Type:        models.Travel,
			Description: "Visited beautiful beaches and pyramids",
			InitialTime: time.Date(2019, 4, 1, 0, 0, 0, 0, time.UTC).Unix(),
			EndTime:     time.Date(2019, 4, 1, 0, 0, 0, 0, time.UTC).Unix(),
			Place:       places["mexico"],
			UserID:      userID,
		},
		{
			Name:   "Spain",
			Type:   models.Travel,
			Place:  places["spain"],
			UserID: userID,
		},
		{
			Name:   "Portugal",
			Type:   models.Travel,
			Place:  places["portugal"],
			UserID: userID,
		},
		{
			Name:   "Netherlands",
			Type:   models.Travel,
			Place:  places["netherlands"],
			UserID: userID,
		},
		{
			Name:   "Denmark",
			Type:   models.Travel,
			Place:  places["denmark"],
			UserID: userID,
		},
		{
			Name:   "Austria",
			Type:   models.Travel,
			Place:  places["austria"],
			UserID: userID,
		},
		{
			Name:   "Hungary",
			Type:   models.Travel,
			Place:  places["hungary"],
			UserID: userID,
		},
		{
			Name:   "Czech Republic",
			Type:   models.Travel,
			Place:  places["czech"],
			UserID: userID,
		},
		{
			Name:   "Germany",
			Type:   models.Travel,
			Place:  places["germany"],
			UserID: userID,
		},
		{
			Name:   "Scotland",
			Type:   models.Travel,
			Place:  places["scotland"],
			UserID: userID,
		},
		{
			Name:   "England",
			Type:   models.Travel,
			Place:  places["england"],
			UserID: userID,
		},
		{
			Name:   "Belgium",
			Type:   models.Travel,
			Place:  places["belgium"],
			UserID: userID,
		},
		{
			Name:   "France",
			Type:   models.Travel,
			Place:  places["france"],
			UserID: userID,
		},
		{
			Name:   "Switzerland",
			Type:   models.Travel,
			Place:  places["switzerland"],
			UserID: userID,
		},
		{
			Name:   "Brazil",
			Type:   models.Travel,
			Place:  places["brazil"],
			UserID: userID,
		},
		{
			Name:   "Chile",
			Type:   models.Travel,
			Place:  places["chile"],
			UserID: userID,
		},
		{
			Name:   "Colombia",
			Type:   models.Travel,
			Place:  places["colombia"],
			UserID: userID,
		},
		{
			Name:   "Uruguay",
			Type:   models.Travel,
			Place:  places["uruguay"],
			UserID: userID,
		},
		{
			Name:   "Poland",
			Type:   models.Travel,
			Place:  places["poland"],
			UserID: userID,
		},
		{
			Name:   "Norway",
			Type:   models.Travel,
			Place:  places["norway"],
			UserID: userID,
		},
		{
			Name:   "Croatia",
			Type:   models.Travel,
			Place:  places["croatia"],
			UserID: userID,
		},
		{
			Name:   "Turkey",
			Type:   models.Travel,
			Place:  places["turkey"],
			UserID: userID,
		},
	}

	// Insert life steps
	for _, step := range lifeSteps {
		_, err := di.DataSource.PostLifeStep(step)
		if err != nil {
			return err
		}
		log.Printf("Created life step: %s", step.Name)
	}

	log.Println("Database initialization completed successfully!")
	return nil
}

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

	initializer := NewDataInitializer(dataSource)
	if err := initializer.InitializeData(); err != nil {
		log.Fatalf("Failed to initialize data: %v", err)
	}
}
