package com.example.database

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.example.models.*
import com.example.plugins.Environment
import com.example.utils.Hasher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import org.litote.kmongo.util.idValue
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MongoAppClient @Inject constructor() {
    private val env: Environment = Environment()
    private val user = env.getVariable("MONGO_USER")
    private val pass = env.getVariable("MONGO_PASS")
    private val host = env.getVariable("MONGO_HOST")
    private val db = env.getVariable("MONGO_DB")
    private val client =
        KMongo.createClient(connectionString = "mongodb+srv://$user:$pass@$host/$db?retryWrites=true&w=majority").coroutine

    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    val rootLogger = loggerContext.getLogger("org.mongodb.driver")

    init {
        rootLogger.level = Level.ERROR
        runBlocking {
            val steps = client.getDatabase(db).getCollection<LifeStep>().find().toList().size
            if (steps == 0) {
                val environment = Environment()
                val user = User.Builder().setUsername("riggoch")
                    .setPassword(
                        Hasher.sha256(
                            environment.getVariable("PASSWORD"),
                            environment.getVariable("AUTH_SALT")
                        )
                    )
                    .setEmail("mateochando@gmail.com").build()
                client.getDatabase(db).getCollection<User>().insertOne(user)
                val insertedUser = client.getDatabase(db).getCollection<User>().find().first()
                println(insertedUser.toString())
                val userId = insertedUser.idValue.toString()

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
                        ).setUserId(userId)
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
                    ).setUserId(userId)
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
                        ).setUserId(userId)
                        .setPlace(sweden).build()
                val contact =
                    Contact.Builder("github.com/ochan12").setEmail("mateochando@gmail.com").setPhone("+460767428890")
                        .build()
                val person = Person.Builder("Mateo", "Ochandorena", contact).setBirthDate(
                    Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis
                ).setUserId(userId).build()

                client.getDatabase(db).getCollection<LifeStep>().insertMany(arrayListOf(qbit, rd, cruncho))
                client.getDatabase(db).getCollection<Person>().insertOne(person)

            }
        }
    }

    fun getSteps(): Flow<LifeStep> {
        return client.getDatabase(db).getCollection<LifeStep>().find().toFlow()
    }

    fun getStepsByType(type: StepType): Flow<LifeStep> {
        return client.getDatabase(db).getCollection<LifeStep>().find(LifeStep::type eq type).toFlow()
    }

    suspend fun createStep(step: LifeStep): String {
        return client.getDatabase(db).getCollection<LifeStep>().insertOne(step).insertedId?.toString() ?: ""
    }

    fun getContactData(): Flow<Contact?> {
        return client.getDatabase(db).getCollection<Contact>().find().toFlow()
    }

    fun getPersonalData(): Flow<Person?> {
        return client.getDatabase(db).getCollection<Person>().find().toFlow()
    }

    fun getUserFromToken(username: String, password: String): Flow<User?> {
        return client.getDatabase(db).getCollection<User>().find(User::password eq password, User::username eq username)
            .limit(1).toFlow()
    }

}

@Module
class MongoAppClientModule {
    @Provides
    fun provide(): MongoAppClient {
        return MongoAppClient()
    }
}