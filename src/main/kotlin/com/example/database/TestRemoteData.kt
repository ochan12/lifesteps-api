package com.example.database

import com.example.models.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class TestRemoteData @Inject constructor() : DataSource {

    val steps = arrayListOf<LifeStep>()
    var contact: Contact? = null
    var person: Person? = null

    init {
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
        val qbit =
            LifeStep.Builder().setName("Qbit")
                .setType(StepType.JOB).setDescription("First job")
                .setPlace(cordoba)
                .setInitialTime(
                    Calendar.Builder().setDate(2018, 5, 1).build().timeInMillis
                ).setEndTime(
                    Calendar.Builder().setDate(2019, 4, 1).build().timeInMillis
                ).setProjects(
                    arrayOf(
                        Project.Builder(
                            "Argentinian Localisation",
                            "Extend Netsuite functionality for Argentina"
                        ).setCompany("Qbit").build(),
                        Project.Builder(
                            "Tax Calculation",
                            "Implement tax-specific calculation and bulk processing of invoices"
                        ).setCompany("Qbit").build()
                    )
                )
                .build()

        val rd = LifeStep.Builder().setName("Reputación digital")
            .setType(StepType.JOB).setDescription("BigData")
            .setInitialTime(
                Calendar.Builder().setDate(2019, 4, 15).build().timeInMillis
            ).setEndTime(
                Calendar.Builder().setDate(2020, 4, 1).build().timeInMillis
            )
            .setProjects(
                arrayOf(
                    Project.Builder(
                        "Twitter/Facebook/YouTube/Instagram/Google News API Ingestion",
                        "Create applications to ingest data in an automated way"
                    ).setCompany("Reputación Digital").build(),
                    Project.Builder(
                        "Convert JS to Python specific Apps",
                        "Replace JS scripts managed by PM2 processes to Python specific apps server-like"
                    ).setCompany("Reputación Digital").build(),
                    Project.Builder(
                        "Dockerization of stack",
                        "Replace monolithic app by microservice apps that can be deployed into a Docker Swarm, create images for each of them"
                    ).setCompany("Reputación Digital").build(),
                    Project.Builder(
                        "Website for client creation",
                        "Develop a website to automate the creation of clients specifying users/keywords and launching/editing scrapers on the fly"
                    ).setCompany("Reputación Digital").build(),
                    Project.Builder(
                        "Management of ELK stack",
                        "In charge of manipulating multiple indexes for different clients, creating analysis graphics and implementing ML Alerts for peaks behaviours"
                    ).setCompany("Reputación Digital").build(),
                    Project.Builder(
                        "Prediction of the 2019 Argentinian presidential election",
                        "Through the Social Media ingested data we created a model to analyse the behaviour of voters and predict the outcome of the election"
                    ).setCompany("Reputación Digital").build()

                )
            )
            .setPlace(cordoba).build()
        val cruncho =
            LifeStep.Builder().setName("Cruncho")
                .setType(StepType.JOB).setDescription("Senior Full Stack Developer")
                .setInitialTime(
                    Calendar.Builder().setDate(2020, 10, 19).build().timeInMillis
                ).setProjects(
                    arrayOf(
                        Project.Builder(
                            "Event Manager",
                            "Implementation of an Event Manager to handle events and publish them into an event calendar"
                        ).setCompany("Cruncho").build(),
                        Project.Builder(
                            "Implement APIs Ingestion",
                            "Use Google / Foursquare / TripAdvisor and other APIs to feed the guides"
                        ).setCompany("Cruncho").build(),
                        Project.Builder(
                            "Guides Features Implementation",
                            "Implement features about content filtering, sorting and improving the quality"
                        ).setCompany("Cruncho").build(),
                        Project.Builder(
                            "Migrate Amplify Database",
                            "Move Amplify to own hosted database"
                        ).setCompany("Cruncho").build(),
                        Project.Builder(
                            "Monorepo implementation",
                            "Migrate multi-repo structure to single monorepo and conditional builds pipeline"
                        ).setCompany("Cruncho").build()
                    )
                )
                .setPlace(sweden).build()
        steps.add(qbit)
        steps.add(rd)
        steps.add(cruncho)

        val rugby =
            LifeStep.Builder().setName("Rugby").setType(StepType.HOBBY).setDescription("Sport").setPlace(cordoba)
                .build()
        steps.add(rugby)

        person = Person.Builder().setName("Mateo").setSurname("Ochandorena")
            .setBirthDate(Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis).build()
        contact = Contact.Builder().setEmail("mateochando@gmail.com").setPhone("+460767428890")
            .setRepository("http://github.com/ochan12").build()
    }

    override fun getSteps(): Flow<LifeStep> {
        return flow {
            steps.map { s -> emit(s) }
        }
    }

    override fun getStepsByType(type: StepType): Flow<LifeStep> {
        return flow {
            steps.filter { s -> s.type == type }.map { s -> emit(s) }
        }
    }

    override suspend fun postStep(step: LifeStep): String {
        steps.add(step)
        return steps.size.toString()
    }

    override suspend fun getContactData(): Flow<Contact?> {
        return flow { contact }
    }

    override suspend fun getPersonalData(): Flow<Person?> {
        return flow { person }
    }
}

@Module
class TestRemoteDataModule {
    @Provides
    fun provide(): TestRemoteData {
        return TestRemoteData()
    }
}