package com.example.database

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.example.models.*
import com.example.plugins.Environment
import com.example.utils.Hasher
import com.mongodb.client.model.Filters
import com.mongodb.client.result.InsertManyResult
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.id.StringId
import org.litote.kmongo.id.toId
import org.litote.kmongo.reactivestreams.KMongo
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
                val userId =
                    client.getDatabase(db).getCollection<User>().findOne(User::username eq "riggoch")?._id.toString()

                val dataInitializer = DataInitializer(userId)
                var companyProjects = dataInitializer.qbitProjects

                println("insert qbit projects")
                // Qbit
                client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val qbit = dataInitializer.buildQbit(companyProjects.map { p -> p._id.toString() })


                // Reputacion Digital
                companyProjects = dataInitializer.rdProjects
                client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val rd = dataInitializer.builRd(companyProjects.map { p -> p._id.toString() })


                // Cruncho
                companyProjects = dataInitializer.crunchoProjects
                client.getDatabase(db).getCollection<Project>().insertMany(companyProjects)
                val cruncho = dataInitializer.builCruncho(companyProjects.map { p -> p._id.toString() })



                client.getDatabase(db).getCollection<LifeStep>().insertMany(arrayListOf(qbit, rd, cruncho))
                client.getDatabase(db).getCollection<Person>().insertOne(dataInitializer.person)
                client.getDatabase(db).getCollection<Resource>().insertMany(dataInitializer.resources)

            }
        }
    }

    fun getSteps(userId: String): Flow<LifeStep> {
        return client.getDatabase(db).getCollection<LifeStep>().find(LifeStep::userId eq userId).toFlow()
    }

    fun getStepsByType(type: StepType, userId: String): Flow<LifeStep> {
        return client.getDatabase(db).getCollection<LifeStep>().find(LifeStep::type eq type, LifeStep::userId eq userId)
            .toFlow()
    }

    suspend fun createStep(step: LifeStep): String {
        return client.getDatabase(db).getCollection<LifeStep>().insertOne(step).insertedId?.toString() ?: ""
    }

    fun getContactData(userId: String): Flow<Contact?> {
        return client.getDatabase(db).getCollection<Contact>().find(LifeStep::userId eq userId).toFlow()
    }

    fun getPersonalData(userId: String): Flow<Person?> {
        return client.getDatabase(db).getCollection<Person>().find(LifeStep::userId eq userId).toFlow()
    }

    fun getUserFromToken(username: String, password: String): Flow<User?> {
        return client.getDatabase(db).getCollection<User>().find(User::password eq password, User::username eq username)
            .limit(1).toFlow()
    }

    fun getProjects(projects: List<String>): Flow<Project?> {
        return client.getDatabase(db).getCollection<Project>()
            .find(Project::_id `in` projects.map { id -> ObjectId(id).toId() }).toFlow()
    }

    fun getResources(resources: List<String>): Flow<Resource?> {
        return client.getDatabase(db).getCollection<Resource>()
            .find(Project::_id `in` resources.map { id -> StringId(id) }).toFlow()
    }

}

@Module
class MongoAppClientModule {
    @Provides
    fun provide(): MongoAppClient {
        return MongoAppClient()
    }
}