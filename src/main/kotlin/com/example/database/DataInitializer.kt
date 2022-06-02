package com.example.database

import com.example.models.*
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
        ),
        Project.Builder(
            "Tax Calculation",
            "Implement tax-specific calculation and bulk processing of invoices"
        )
    ).map { builder -> builder.setUserId(userId).build() }

    val rdProjects = listOf(
        Project.Builder(
            "Twitter/Facebook/YouTube/Instagram/Google News API Ingestion",
            "Create applications to ingest data in an automated way"
        ),
        Project.Builder(
            "Convert JS to Python specific Apps",
            "Replace JS scripts managed by PM2 processes to Python specific apps server-like"
        ),
        Project.Builder(
            "Dockerization of stack",
            "Replace monolithic app by microservice apps that can be deployed into a Docker Swarm, create images for each of them"
        ),
        Project.Builder(
            "Website for client creation",
            "Develop a website to automate the creation of clients specifying users/keywords and launching/editing scrapers on the fly"
        ),
        Project.Builder(
            "Management of ELK stack",
            "In charge of manipulating multiple indexes for different clients, creating analysis graphics and implementing ML Alerts for peaks behaviours"
        ),
        Project.Builder(
            "Prediction of the 2019 Argentinian presidential election",
            "Through the Social Media ingested data we created a model to analyse the behaviour of voters and predict the outcome of the election"
        )
    ).map { builder -> builder.setUserId(userId).build() }

    val crunchoProjects = listOf(
        Project.Builder(
            "Event Manager",
            "Implementation of an Event Manager to handle events and publish them into an event calendar"
        ),
        Project.Builder(
            "Implement APIs Ingestion",
            "Use Google / Foursquare / TripAdvisor and other APIs to feed the guides"
        ),
        Project.Builder(
            "Guides Features Implementation",
            "Implement features about content filtering, sorting and improving the quality"
        ),
        Project.Builder(
            "Migrate Amplify Database",
            "Move Amplify to own hosted database"
        ),
        Project.Builder(
            "Monorepo implementation",
            "Migrate multi-repo structure to single monorepo and conditional builds pipeline"
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
                Calendar.Builder().setDate(2019, 4, 15).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2020, 4, 1).build().timeInMillis
            )
            .setProjects(projectsId).setUserId(userId)
            .setPlace(cordoba).build()
    }

    fun builCruncho(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Cruncho")
            .setType(StepType.JOB).setDescription("Senior Full Stack Developer")
            .setInitialTime(
                Calendar.Builder().setDate(2020, 10, 19).build().timeInMillis
            ).setProjects(
                projectsId
            ).setUserId(userId)
            .setPlace(sweden).build()
    }

    fun buildQbit(projectsId: List<String>): LifeStep {
        return LifeStep.Builder().setName("Qbit")
            .setType(StepType.JOB).setDescription("First job")
            .setPlace(cordoba)
            .setInitialTime(
                Calendar.Builder().setDate(2018, 5, 1).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2019, 4, 1).build().timeInMillis
            ).setProjects(
                projectsId
            ).setUserId(userId)
            .build()
    }
}