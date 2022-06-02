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
                val insertedUser = client.getDatabase(db).getCollection<User>().insertOne(user)
                val userId = insertedUser.idValue.toString()

                val dataInitializer = DataInitializer(userId)

                // Qbit
                var companyProjects = dataInitializer.qbitProjects
                var inserterdProjects = client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val qbit = dataInitializer.buildQbit(inserterdProjects.insertedIds.values.toList().map { toString() })

                // Reputacion Digital
                companyProjects = dataInitializer.rdProjects
                inserterdProjects = client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val rd = dataInitializer.builRd(inserterdProjects.insertedIds.values.toList().map { toString() })

                // Cruncho
                companyProjects = dataInitializer.crunchoProjects
                inserterdProjects = client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val cruncho = dataInitializer.builCruncho(inserterdProjects.insertedIds.values.toList().map { toString() })


                client.getDatabase(db).getCollection<LifeStep>().insertMany(arrayListOf(qbit, rd, cruncho))
                client.getDatabase(db).getCollection<Person>().insertOne(dataInitializer.person)

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