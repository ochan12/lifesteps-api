package com.example.database

import com.example.models.*
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId
import org.litote.kmongo.id.toId
import java.util.*

data class DataInitializer(val userId: String) {

    val cordoba = Place(
        "Córdoba",
        GeoPosition(lat = (-31.4135).toLong(), lon = (-64.18105).toLong()),
        Locale("es", "AR").isO3Country
    )

    val sweden = Place(
        "Stockholm",
        GeoPosition(lat = (59.33258).toLong(), lon = (18.0649).toLong()),
        Locale("sv", "SE").isO3Country
    )
    val qbitProjects = listOf(
        Project.Builder(
            "Argentinian Localisation",
            "Extend Netsuite functionality for Argentina"
        ).setResources(listOf("javascript", "netsuite")),
        Project.Builder(
            "Tax Calculation",
            "Implement tax-specific calculation and bulk processing of invoices"
        ).setResources(listOf("javascript", "netsuite"))
    ).map { builder -> builder.setUserId(userId).build() }

    val rdProjects = listOf(
        Project.Builder(
            "API Ingestion",
            "Create applications to ingest data from Twitter, Facebook, YouTube, Instagram, Google News APIs in an automated way"
        ).setResources(listOf("python", "gunicorn")),
        Project.Builder(
            "Convert JS to Python specific Apps",
            "Replace JS scripts managed by PM2 processes to Python specific apps server-like"
        ).setResources(listOf("python", "typescript")),
        Project.Builder(
            "Dockerization of stack",
            "Replace monolithic app by microservice apps that can be deployed into a Docker Swarm, create images for each of them"
        ).setResources(listOf("docker", "traefik")),
        Project.Builder(
            "Website for client creation",
            "Develop a website to automate the creation of clients specifying users/keywords and launching/editing scrapers on the fly"
        ).setResources(listOf("react", "typescript")),
        Project.Builder(
            "Management of ELK stack",
            "In charge of manipulating multiple indexes for different clients, creating analysis graphics and implementing ML Alerts for peaks behaviours"
        ).setResources(listOf("elastic")),
        Project.Builder(
            "Prediction of the 2019 Argentinian presidential election",
            "Through the Social Media ingested data we created a model to analyse the behaviour of voters and predict the outcome of the election"
        ).setResources(listOf("elastic"))
    ).map { builder -> builder.setUserId(userId).build() }

    val crunchoProjects = listOf(
        Project.Builder(
            "Event Manager",
            "Implementation of an Event Manager to handle events and publish them into an event calendar"
        ).setUrl("https://cruncho.com/event-calendar/").setResources(listOf("aws", "mongodb", "react")),
        Project.Builder(
            "Implement APIs Ingestion",
            "Use Google / Foursquare / TripAdvisor and other APIs to feed the guides"
        ).setResources(listOf("express", "redis", "node", "mongodb")),
        Project.Builder(
            "Guides Features Implementation",
            "Implement features about content filtering, sorting and improving the quality"
        ).setUrl("https://stockholm.cruncho.co/").setResources(listOf("react", "redux", "typescript", "sass")),
        Project.Builder(
            "Migrate Amplify Database",
            "Move Amplify to own hosted database"
        ).setResources(listOf("aws", "mongodb", "traefik")),
        Project.Builder(
            "Monorepo implementation",
            "Migrate multi-repo structure to single monorepo and conditional builds pipeline"
        ).setResources(listOf("turborepo", "bitbucket", "docker", "webpack")),
        Project.Builder(
            "Events API",
            "Created an API to be consumed directly by our clients with token authorization and content filtering"
        ).setUrl("https://events-api.cruncho.co/swagger").setResources(listOf("redis", "mongodb", "fastify", "node", "docker", "typescript")),
        Project.Builder(
            "Guides API",
            "Developed features for internal consumption of our own guides. "
        ).setResources(
            listOf(
                "redis",
                "mongodb",
                "fastify",
                "elastic",
                "traefik",
                "node",
                "docker",
                "typescript",
                "postgresql"
            )
        )
    ).map { builder -> builder.setUserId(userId).build() }

    val contact = Contact.Builder().setRepository("https://github.com/ochan12").setEmail("mateochando@gmail.com")
        .setPhone("+460767428890").setLinkedIn("https://www.linkedin.com/in/m-ochandorena/")
        .build()

    val person = Person.Builder("Mateo", "Ochandorena", contact).setBirthDate(
        Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis
    ).setUserId(userId).build()

    fun builRd(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Reputación digital")
            .setType(StepType.JOB).setDescription("BigData")
            .setInitialTime(
                Calendar.Builder().setDate(2019, 3, 15).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2020, 3, 1).build().timeInMillis
            )
            .setPhotos(listOf("/img/jobs/rd.png"))
            .setProjects(projectsId).setUserId(userId)
            .setPlace(cordoba).build()
    }

    fun builCruncho(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Cruncho")
            .setType(StepType.JOB).setDescription("Senior Full Stack Developer")
            .setInitialTime(
                Calendar.Builder().setDate(2020, 9, 19).build().timeInMillis
            ).setProjects(
                projectsId
            ).setUserId(userId)
            .setPhotos(listOf("/img/jobs/cruncho.png"))
            .setPlace(sweden).build()
    }

    fun buildQbit(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Qbit")
            .setType(StepType.JOB).setDescription("First job")
            .setPlace(cordoba)
            .setInitialTime(
                Calendar.Builder().setDate(2018, 4, 1).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2019, 3, 1).build().timeInMillis
            ).setProjects(
                projectsId
            ).setUserId(userId)
            .setPhotos(listOf("/img/jobs/qbit.png"))
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
        ),
        Resource(
            "Node.js",
            "/img/logos/logo_node.png",
            "https://nodejs.org/en/",
            StringId("node")
        ),
        Resource(
            "Docker",
            "/img/logos/logo_docker.png",
            "https://www.docker.com/",
            StringId("docker")
        ),
        Resource(
            "ReactJS",
            "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg",
            "https://reactjs.org/",
            StringId("react")
        ),
        Resource(
            "AWS",
            "https://upload.wikimedia.org/wikipedia/commons/9/93/Amazon_Web_Services_Logo.svg",
            "https://aws.amazon.com/",
            StringId("aws")
        ),
        Resource(
            "Elastic",
            "/img/logos/logo_elastic.png",
            "https://www.elastic.co/",
            StringId("elastic")
        ),
        Resource(
            "Redis",
            "/img/logos/logo_redis.png",
            "https://redis.io/",
            StringId("redis")
        ),
        Resource(
            "Turborepo",
            "https://user-images.githubusercontent.com/4060187/106504110-82f58d00-6494-11eb-87b7-a16d4f68bc5a.png",
            "https://turborepo.org/",
            StringId("turborepo")
        ),
        Resource(
            "NetSuite",
            "https://upload.wikimedia.org/wikipedia/commons/9/95/Oracle_NetSuite_2021.png",
            "https://www.netsuite.com/portal/home.shtml",
            StringId("netsuite")
        ),
        Resource(
            "Python",
            "https://upload.wikimedia.org/wikipedia/commons/c/c3/Python-logo-notext.svg",
            "https://www.python.org/",
            StringId("python")
        ),
        Resource(
            "Firebase",
            "https://upload.wikimedia.org/wikipedia/commons/4/46/Touchicon-180.png",
            "https://firebase.google.com/",
            StringId("firebase")
        ),
        Resource(
            "Java",
            "/img/logos/logo_java.png",
            "https://www.java.com/en/",
            StringId("java")
        ),
        Resource(
            "Express",
            "https://upload.wikimedia.org/wikipedia/commons/6/64/Expressjs.png",
            "https://expressjs.com/",
            StringId("expressjs")
        ),
        Resource(
            "PostgreSQL",
            "https://upload.wikimedia.org/wikipedia/commons/2/29/Postgresql_elephant.svg",
            "https://www.postgresql.org/",
            StringId("postgresql")
        ),
        Resource(
            "Webpack",
            "/img/logos/logo_webpack.png",
            "https://webpack.js.org/",
            StringId("webpack")
        ),
        Resource(
            "SASS",
            "https://sass-lang.com/assets/img/logos/logo-b6e1ef6e.svg",
            "https://sass-lang.com/",
            StringId("sass")
        ),
        Resource(
            "git",
            "https://upload.wikimedia.org/wikipedia/commons/3/3f/Git_icon.svg",
            "https://git-scm.com/",
            StringId("git")
        ),
        Resource(
            "Bitbucket",
            "https://upload.wikimedia.org/wikipedia/commons/0/0e/Bitbucket-blue-logomark-only.svg",
            "https://bitbucket.org/",
            StringId("bitbucket")
        ),
        Resource(
            "Heroku",
            "https://upload.wikimedia.org/wikipedia/commons/e/ec/Heroku_logo.svg",
            "https://heroku.com",
            StringId("heroku")
        ),
        Resource(
            "Kotlin",
            "https://upload.wikimedia.org/wikipedia/commons/0/06/Kotlin_Icon.svg",
            "https://kotlinlang.org/",
            StringId("kotlin")
        ),
        Resource(
            "Android",
            "https://upload.wikimedia.org/wikipedia/commons/3/3e/Android_logo_2019.png",
            "https://www.android.com/",
            StringId("android")
        ),
        Resource(
            "Dagger",
            "dagger",
            "https://dagger.dev/",
            StringId("dagger")
        ),
        Resource(
            "Redux",
            "/img/logos/logo_redux.svg",
            "https://redux.js.org/",
            StringId("redux")
        ),
        Resource(
            "Nginx",
            "https://upload.wikimedia.org/wikipedia/commons/c/c5/Nginx_logo.svg",
            "https://www.nginx.com/",
            StringId("nginx")
        ),
        Resource(
            "Traefik",
            "https://upload.wikimedia.org/wikipedia/commons/1/1b/Traefik.logo.png",
            "https://traefik.io/",
            StringId("traefik")
        ),
        Resource(
            "Fastify",
            "/img/logos/logo_fastify.png",
            "https://www.fastify.io/",
            StringId("fastify")
        ),
        Resource(
            "Gunicorn",
            "/img/logos/logo_gunicorn.png",
            "https://gunicorn.org/",
            StringId("gunicorn")
        ),
        Resource(
            "MongoDB",
            "/img/logos/logo_mongo.svg",
            "https://www.mongodb.com/",
            StringId("mongodb")
        )
    )


}