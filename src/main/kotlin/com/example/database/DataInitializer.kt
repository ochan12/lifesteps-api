package com.example.database

import com.example.models.*
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId
import org.litote.kmongo.id.toId
import java.util.*

data class DataInitializer(val userId: String) {

    val cordoba = Place(
        "Córdoba", GeoPosition(lat = (-31.4135).toLong(), lon = (-64.18105).toLong()), Locale("es", "AR").isO3Country
    )

    val sweden = Place(
        "Stockholm", GeoPosition(lat = (59.33258).toLong(), lon = (18.0649).toLong()), Locale("sv", "SE").isO3Country
    )
    val italy = Place(
        "Siracuse", GeoPosition(lat = (37.075474).toLong(), lon = (15.286586).toLong()), Locale("it", "IT").isO3Country
    )

    val mexico = Place(
        "Mexico", GeoPosition(lat = (20.611313).toLong(), lon = (-87.084000).toLong()), Locale("es", "MX").isO3Country
    )
    val spain = Place(
        "Spain", GeoPosition(lat = (40.2085).toLong(), lon = (-3.713).toLong()), Locale("es", "ES").isO3Country
    )
    val france = Place(
        "France", GeoPosition(lat = (46.2276).toLong(), lon = (2.349014).toLong()), Locale("fr", "FR").isO3Country
    )
    val belgium = Place(
        "Belgium", GeoPosition(lat = (51.049999).toLong(), lon = (3.733333).toLong()), Locale("nl", "BE").isO3Country
    )
    val england = Place(
        "England", GeoPosition(lat = (51.503399).toLong(), lon = (-0.119519).toLong()), Locale("en", "GB").isO3Country
    )
    val scotland = Place(
        "Scotland", GeoPosition(lat = (55.948612).toLong(), lon = (-3.200833).toLong()), Locale("en", "GB").isO3Country
    )
    val germany = Place(
        "Germany", GeoPosition(lat = (52.518898).toLong(), lon = (13.401797).toLong()), Locale("de", "DE").isO3Country
    )
    val czech = Place(
        "Czech Republic",
        GeoPosition(lat = (50.073658).toLong(), lon = (14.418540).toLong()),
        Locale("cz", "CZ").isO3Country
    )
    val switzerland = Place(
        "Switzerland",
        GeoPosition(lat = (47.451542).toLong(), lon = (8.564572).toLong()),
        Locale("de", "CH").isO3Country
    )
    val portugal = Place(
        "Portugal",
        GeoPosition(lat = (38.736946).toLong(), lon = (-9.142685).toLong()),
        Locale("pt", "PT").isO3Country
    )
    val hungary = Place(
        "Hungary",
        GeoPosition(lat = (47.497913).toLong(), lon = (19.040236).toLong()),
        Locale("hu", "HU").isO3Country
    )
    val austria = Place(
        "Austria",
        GeoPosition(lat = (48.210033).toLong(), lon = (16.363449).toLong()),
        Locale("de", "AT").isO3Country
    )
    val denmark = Place(
        "Denmark", GeoPosition(
            lat = (55.620750).toLong(), lon = (12.650462
                    ).toLong()
        ), Locale("da", "DK").isO3Country
    )
    val netherlands = Place(
        "Netherlands",
        GeoPosition(lat = (52.308056).toLong(), lon = (4.764167).toLong()),
        Locale("nl", "NL").isO3Country
    )
    val colombia = Place(
        "Colombia",
        GeoPosition(lat = (4.624335).toLong(), lon = (-74.063644).toLong()),
        Locale("es", "CO").isO3Country
    )

    val chile = Place(
        "Chile",
        GeoPosition(lat = (-33.447487).toLong(), lon = (-70.673676).toLong()),
        Locale("es", "CL").isO3Country
    )
    val brazil = Place(
        "Brazil",
        GeoPosition(lat = (-15.793889).toLong(), lon = (-47.882778).toLong()),
        Locale("pt", "BR").isO3Country
    )
    val uruguay = Place(
        "Uruguay",
        GeoPosition(lat = (-34.90328).toLong(), lon = (-56.18816).toLong()),
        Locale("es", "UY").isO3Country
    )

    val poland = Place(
        "Poland",
        GeoPosition(lat = (51.9194).toLong(), lon = (19.1451).toLong()),
        Locale("pl", "PL").isO3Country
    )

    val norway = Place(
        "Norway",
        GeoPosition(lat = (60.4720).toLong(), lon = (8.4689).toLong()),
        Locale("no", "NO").isO3Country
    )

    val qbitProjects = listOf(
        Project.Builder(
            "Argentinian Localisation", "Extend Netsuite functionality for Argentina"
        ).setResources(listOf("javascript", "netsuite")), Project.Builder(
            "Tax Calculation", "Implement tax-specific calculation and bulk processing of invoices"
        ).setResources(listOf("javascript", "netsuite"))
    ).map { builder -> builder.setUserId(userId).setCompany("Qbit").build() }

    val rdProjects = listOf(
        Project.Builder(
            "API Ingestion",
            "Create applications to ingest data from Twitter, Facebook, YouTube, Instagram, Google News APIs in an automated way"
        ).setResources(listOf("python", "gunicorn")), Project.Builder(
            "Convert JS to Python specific Apps",
            "Replace JS scripts managed by PM2 processes to Python specific apps server-like"
        ).setResources(listOf("python", "typescript")), Project.Builder(
            "Dockerization of stack",
            "Replace monolithic app by microservice apps that can be deployed into a Docker Swarm, create images for each of them"
        ).setResources(listOf("docker", "traefik")), Project.Builder(
            "Website for client creation",
            "Develop a website to automate the creation of clients specifying users/keywords and launching/editing scrapers on the fly"
        ).setResources(listOf("react", "typescript")), Project.Builder(
            "Management of ELK stack",
            "In charge of manipulating multiple indexes for different clients, creating analysis graphics and implementing ML Alerts for peaks behaviours"
        ).setResources(listOf("elastic")), Project.Builder(
            "Prediction of the 2019 Argentinian presidential election",
            "Through the Social Media ingested data we created a model to analyse the behaviour of voters and predict the outcome of the election"
        ).setResources(listOf("elastic"))
    ).map { builder -> builder.setUserId(userId).setCompany("Reputación digital").build() }

    val crunchoProjects = listOf(
        Project.Builder(
            "Event Manager",
            "Implementation of an Event Manager to handle events and publish them into an event calendar"
        ).setUrl("https://cruncho.com/event-calendar/").setResources(listOf("aws", "mongodb", "react")),
        Project.Builder(
            "Implement APIs Ingestion", "Use Google / Foursquare / TripAdvisor and other APIs to feed the guides"
        ).setResources(listOf("express", "redis", "node", "mongodb")),
        Project.Builder(
            "Guides Features Implementation",
            "Implement features about content filtering, sorting and improving the quality"
        ).setUrl("https://stockholm.cruncho.co/").setResources(listOf("react", "redux", "typescript", "sass")),
        Project.Builder(
            "Migrate Amplify Database", "Move Amplify to own hosted database"
        ).setResources(listOf("aws", "mongodb", "traefik")),
        Project.Builder(
            "Monorepo implementation", "Migrate multi-repo structure to single monorepo and conditional builds pipeline"
        ).setResources(listOf("turborepo", "bitbucket", "docker", "webpack")),
        Project.Builder(
            "Events API",
            "Created an API to be consumed directly by our clients with token authorization and content filtering"
        ).setUrl("https://events-api.cruncho.co/swagger")
            .setResources(listOf("redis", "mongodb", "fastify", "node", "docker", "typescript")),
        Project.Builder(
            "Guides API", "Developed features for internal consumption of our own guides. "
        ).setResources(
            listOf(
                "redis", "mongodb", "fastify", "elastic", "traefik", "node", "docker", "typescript", "postgresql"
            )
        )
    ).map { builder -> builder.setUserId(userId).setCompany("Cruncho").build() }

    val soloProjects = listOf<Project>(
        Project.Builder("Lifestep API", "Create a simple API to fetch data for my website portfolio")
            .setCompany("Solo")
            .setResources(
                listOf("kotlin", "mongodb")
            ).setUserId(userId).setUrl("https://lifesteps-api.herokuapp.com/").build(),
        Project.Builder("My website", "YOU'RE HERE!").setResources(listOf("reactjs", "nextjs", "sass", "typescript"))
            .setUserId(userId)
            .setCompany("Solo").setUrl("https://riggoch.vercel.app/").build(),
        Project.Builder(
            "myLook",
            "Thesis graduation project to promote small clothing stores reaching out to clients. This was a group project where I took mainly of Android features"
        )
            .setResources(listOf("android", "firebase")).setUserId(userId)
            .setCompany("Solo").setUrl("https://github.com/myLook2018/myLook").build(),
        Project.Builder("TimeTracker", "Android app to track periods of time")
            .setResources(listOf("android", "firebase", "dagger", "kotlin")).setUserId(userId)
            .setUrl("https://github.com/ochan12/TimeTracker")
            .setCompany("Solo").build()
    )

    val tracabProjects = listOf<Project>(
        Project.Builder("Match Simulator", "Create an application to allow game streaming through internal tools")
            .setCompany("Tracab")
            .setResources(
                listOf("node", "redis", "typescript")
            ).setUserId(userId).build(),
        Project.Builder("Backend tasks", "Complete different tasks including bugs, features, endpoints for internal and external usage")
            .setCompany("Tracab")
            .setUserId(userId)
            .setResources(listOf("redis", "mongodb", "express", "javascript", "typescript", "react", "docker", "kubernetes", "bitbucket", "nginx", "kind", "terraform"))
            .build()
    )

    val contact = Contact.Builder().setRepository("https://github.com/ochan12").setEmail("mateochando@gmail.com")
        .setPhone("+460767428890").setLinkedIn("https://www.linkedin.com/in/m-ochandorena/").build()

    val person = Person.Builder("Mateo", "Ochandorena", contact).setBirthDate(
        Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis
    ).setUserId(userId).build()

    val argentinaTravel = LifeStep.Builder().setName("Argentina").setType(StepType.TRAVEL)
        .setDescription("Home country, place of the wines and Fernet").setInitialTime(
            Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis
        ).setEndTime(
            Calendar.Builder().setDate(2020, 3, 1).build().timeInMillis
        ).setPlace(cordoba).setUserId(userId).build()

    val swedenTravel =
        LifeStep.Builder().setName("Sweden").setDescription("Currently living here").setType(StepType.TRAVEL)
            .setInitialTime(
                Calendar.Builder().setDate(2020, 9, 19).build().timeInMillis
            ).setUserId(userId).setPlace(sweden).build()

    val italyTravel = LifeStep.Builder().setName("Italy").setDescription("Got my citizenship over pizza and pasta")
        .setType(StepType.TRAVEL).setUserId(userId).setInitialTime(
            Calendar.Builder().setDate(2021, 4, 15).build().timeInMillis
        ).setEndTime(Calendar.Builder().setDate(2021, 9, 15).build().timeInMillis).setPlace(italy).build()

    val mexicoTravel = LifeStep.Builder().setName("México").setDescription("Visited beautiful beaches and pyramids")
        .setType(StepType.TRAVEL).setUserId(userId).setPlace(mexico).setInitialTime(
            Calendar.Builder().setDate(2019, 4, 1).build().timeInMillis
        ).setEndTime(Calendar.Builder().setDate(2019, 4, 1).build().timeInMillis).build()

    val spainTravel =
        LifeStep.Builder().setName("Spain").setType(StepType.TRAVEL).setUserId(userId).setPlace(spain).build()
    val portugalTravel =
        LifeStep.Builder().setName("Portugal").setType(StepType.TRAVEL).setUserId(userId).setPlace(portugal).build()
    val netherlandsTravel =
        LifeStep.Builder().setName("Netherlands").setType(StepType.TRAVEL).setUserId(userId).setPlace(netherlands)
            .build()
    val denmarkTravel =
        LifeStep.Builder().setName("Denmark").setType(StepType.TRAVEL).setUserId(userId).setPlace(denmark).build()
    val austriaTravel =
        LifeStep.Builder().setName("Austria").setType(StepType.TRAVEL).setUserId(userId).setPlace(austria).build()
    val hungaryTravel =
        LifeStep.Builder().setName("Hungary").setType(StepType.TRAVEL).setUserId(userId).setPlace(hungary).build()
    val czechTravel =
        LifeStep.Builder().setName("Czech Republic").setType(StepType.TRAVEL).setUserId(userId).setPlace(czech).build()
    val germanyTravel =
        LifeStep.Builder().setName("Germany").setType(StepType.TRAVEL).setUserId(userId).setPlace(germany).build()
    val scotlandTravel =
        LifeStep.Builder().setName("Scotland").setType(StepType.TRAVEL).setUserId(userId).setPlace(scotland).build()
    val englandTravel =
        LifeStep.Builder().setName("England").setType(StepType.TRAVEL).setUserId(userId).setPlace(england).build()
    val belgiumTravel =
        LifeStep.Builder().setName("Belgium").setType(StepType.TRAVEL).setUserId(userId).setPlace(belgium).build()
    val franceTravel =
        LifeStep.Builder().setName("France").setType(StepType.TRAVEL).setUserId(userId).setPlace(france).build()
    val switzerlandTravel =
        LifeStep.Builder().setName("Switzerland").setType(StepType.TRAVEL).setUserId(userId).setPlace(switzerland)
            .build()
    val brazilTravel =
        LifeStep.Builder().setName("Brazil").setType(StepType.TRAVEL).setUserId(userId).setPlace(brazil)
            .build()
    val chileTravel =
        LifeStep.Builder().setName("Switzerland").setType(StepType.TRAVEL).setUserId(userId).setPlace(chile)
            .build()
    val colombiaTravel =
        LifeStep.Builder().setName("Colombia").setType(StepType.TRAVEL).setUserId(userId).setPlace(colombia)
            .build()
    val uruguayTravel =
        LifeStep.Builder().setName("Uruguay").setType(StepType.TRAVEL).setUserId(userId).setPlace(uruguay)
            .build()
    val polandTravel =
        LifeStep.Builder().setName("Poland").setType(StepType.TRAVEL).setUserId(userId).setPlace(poland)
            .build()
    val norwayTravel =
        LifeStep.Builder().setName("Norway").setType(StepType.TRAVEL).setUserId(userId).setPlace(norway)
            .build()


    val travelResources: List<LifeStep> =
        listOf(
            spainTravel,
            portugalTravel,
            netherlandsTravel,
            denmarkTravel,
            austriaTravel,
            hungaryTravel,
            czechTravel,
            germanyTravel,
            scotlandTravel,
            englandTravel,
            belgiumTravel,
            franceTravel,
            switzerlandTravel,
            swedenTravel,
            italyTravel, argentinaTravel, mexicoTravel, brazilTravel, colombiaTravel, chileTravel, uruguayTravel,
            norwayTravel, polandTravel
        )

    fun buildRd(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Reputación digital").setType(StepType.JOB)
            .setDescription("Gather data from different social media platforms to analyse the impact of social actors/companies")
            .setInitialTime(
                Calendar.Builder().setDate(2019, 3, 15).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2020, 3, 1).build().timeInMillis
            ).setPhotos(listOf("/img/jobs/rd.png")).setProjects(projectsId).setUserId(userId).setPlace(cordoba).build()
    }

    fun buildSolo(projectsId: List<String>): LifeStep {
        return LifeStep.Builder("Solo projects").setType(StepType.HOBBY).setInitialTime(
            Calendar.Builder().setDate(2019, 3, 15).build().timeInMillis
        ).setDescription("Couple of projects just to have fun").setPhotos(listOf("/img/jobs/solo.svg"))
            .setUserId(userId).setProjects(projectsId).build()
    }

    fun buildCruncho(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Cruncho").setType(StepType.JOB)
            .setDescription("Create city guides grouping data from different APIs")
            .setInitialTime(
                Calendar.Builder().setDate(2020, 9, 19).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2022, 10, 1).build().timeInMillis
            )
            .setProjects(
                projectsId
            ).setUserId(userId).setPhotos(listOf("/img/jobs/cruncho.png")).setPlace(sweden).build()
    }

    fun buildQbit(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Qbit").setType(StepType.JOB)
            .setDescription("Provide B2B services on an ERP suite").setPlace(cordoba)
            .setInitialTime(
                Calendar.Builder().setDate(2018, 4, 1).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2019, 3, 1).build().timeInMillis
            ).setProjects(
                projectsId
            ).setUserId(userId).setPhotos(listOf("/img/jobs/qbit.png")).build()
    }

    fun buildTracab(projectsId: List<String>):LifeStep {
        return LifeStep.Builder().setName("Tracab").setType(StepType.JOB)
            .setDescription("Provide sports statistics for clients")
            .setPlace(sweden)
            .setInitialTime(
                Calendar.Builder().setDate(2022, 10, 2).build().timeInMillis
            ).setPhotos(
                listOf("/img/jobs/tracab.png")
            ).setProjects(projectsId)
            .setUserId(userId)
            .build()
    }

    val resources: List<Resource> = listOf(
        Resource(
            "TypeScript",
            "https://upload.wikimedia.org/wikipedia/commons/4/4c/Typescript_logo_2020.svg",
            "https://www.typescriptlang.org/",
            StringId("typescript")
        ), Resource(
            "JavaScript",
            "https://upload.wikimedia.org/wikipedia/commons/3/3b/Javascript_Logo.png",
            "https://www.javascript.com/",
            StringId("javascript")
        ), Resource(
            "Node.js", "/img/logos/logo_node.png", "https://nodejs.org/en/", StringId("node")
        ), Resource(
            "Docker", "/img/logos/logo_docker.png", "https://www.docker.com/", StringId("docker")
        ), Resource(
            "ReactJS",
            "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg",
            "https://reactjs.org/",
            StringId("react")
        ), Resource(
            "AWS",
            "https://upload.wikimedia.org/wikipedia/commons/9/93/Amazon_Web_Services_Logo.svg",
            "https://aws.amazon.com/",
            StringId("aws")
        ), Resource(
            "Elastic", "/img/logos/logo_elastic.png", "https://www.elastic.co/", StringId("elastic")
        ), Resource(
            "Redis", "/img/logos/logo_redis.png", "https://redis.io/", StringId("redis")
        ), Resource(
            "Turborepo",
            "https://user-images.githubusercontent.com/4060187/106504110-82f58d00-6494-11eb-87b7-a16d4f68bc5a.png",
            "https://turborepo.org/",
            StringId("turborepo")
        ), Resource(
            "NetSuite",
            "https://upload.wikimedia.org/wikipedia/commons/9/95/Oracle_NetSuite_2021.png",
            "https://www.netsuite.com/portal/home.shtml",
            StringId("netsuite")
        ), Resource(
            "Python",
            "https://upload.wikimedia.org/wikipedia/commons/c/c3/Python-logo-notext.svg",
            "https://www.python.org/",
            StringId("python")
        ), Resource(
            "Firebase",
            "https://upload.wikimedia.org/wikipedia/commons/4/46/Touchicon-180.png",
            "https://firebase.google.com/",
            StringId("firebase")
        ), Resource(
            "Java", "/img/logos/logo_java.png", "https://www.java.com/en/", StringId("java")
        ), Resource(
            "Express",
            "https://upload.wikimedia.org/wikipedia/commons/6/64/Expressjs.png",
            "https://expressjs.com/",
            StringId("expressjs")
        ), Resource(
            "PostgreSQL",
            "https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg",
            "https://www.postgresql.org/",
            StringId("postgresql")
        ), Resource(
            "Webpack", "/img/logos/logo_webpack.png", "https://webpack.js.org/", StringId("webpack")
        ), Resource(
            "SASS",
            "https://upload.wikimedia.org/wikipedia/commons/9/96/Sass_Logo_Color.svg",
            "https://sass-lang.com/",
            StringId("sass")
        ), Resource(
            "git",
            "https://upload.wikimedia.org/wikipedia/commons/3/3f/Git_icon.svg",
            "https://git-scm.com/",
            StringId("git")
        ), Resource(
            "Bitbucket",
            "https://upload.wikimedia.org/wikipedia/commons/0/0e/Bitbucket-blue-logomark-only.svg",
            "https://bitbucket.org/",
            StringId("bitbucket")
        ), Resource(
            "Heroku",
            "/img/logos/logo_heroku.svg",
            "https://heroku.com",
            StringId("heroku")
        ), Resource(
            "Kotlin",
            "https://upload.wikimedia.org/wikipedia/commons/0/06/Kotlin_Icon.svg",
            "https://kotlinlang.org/",
            StringId("kotlin")
        ), Resource(
            "Android",
            "/img/logos/logo_android.svg",
            "https://www.android.com/",
            StringId("android")
        ), Resource(
            "Dagger", "", "https://dagger.dev/", StringId("dagger")
        ), Resource(
            "Redux", "/img/logos/logo_redux.svg", "https://redux.js.org/", StringId("redux")
        ), Resource(
            "Nginx",
            "https://upload.wikimedia.org/wikipedia/commons/c/c5/Nginx_logo.svg",
            "https://www.nginx.com/",
            StringId("nginx")
        ), Resource(
            "Traefik",
            "https://upload.wikimedia.org/wikipedia/commons/1/1b/Traefik.logo.png",
            "https://traefik.io/",
            StringId("traefik")
        ), Resource(
            "Fastify", "/img/logos/logo_fastify.png", "https://www.fastify.io/", StringId("fastify")
        ), Resource(
            "Gunicorn", "/img/logos/logo_gunicorn.png", "https://gunicorn.org/", StringId("gunicorn")
        ), Resource(
            "MongoDB", "/img/logos/logo_mongo.svg", "https://www.mongodb.com/", StringId("mongodb")
        ), Resource(
            "Next.js", "/img/logos/logo_nextjs.png", "https://nextjs.org/", StringId("nextjs")
        ),
        Resource(
            "Terraform",
            "img/logos/terraform.png",
            "https://www.terraform.io/",
            StringId("terraform")
        ),
        Resource("Kubernetes", "https://upload.wikimedia.org/wikipedia/commons/3/39/Kubernetes_logo_without_workmark.svg", "https://kubernetes.io/", StringId("kubernetes")),
        Resource("KIND", "https://upload.wikimedia.org/wikipedia/commons/f/f6/KinD_logo.png", "https://kind.sigs.k8s.io/", StringId("kind"))
    )


}