package com.example.database

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.example.models.*
import com.example.plugins.Environment
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import org.slf4j.LoggerFactory
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
//        val lifeStep = LifeStep.Builder().setName("Qbit").setType(StepType.JOB).build()
//        val person = Person.Builder().setName("Mateo").setSurname("Ochandorena")
//            .setBirthDate(Calendar.Builder().setDate(1996, 2, 29).build().timeInMillis).build()
//        val contact = Contact.Builder().setEmail("mateochando@gmail.com").setPhone("+3512234769")
//            .setRepository("http://github.com/ochan12").build()
//        runBlocking {
//            client.getDatabase(db).getCollection<LifeStep>().deleteMany(LifeStep::type eq StepType.JOB)
//            client.getDatabase(db).getCollection<LifeStep>().insertOne(lifeStep)
//            client.getDatabase(db).getCollection<Contact>().insertOne(contact)
//            client.getDatabase(db).getCollection<Person>().insertOne(person)
//        }
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

    fun getUserFromToken(token: String): Flow<User?> {
        return client.getDatabase(db).getCollection<User>().find(User::token eq token).limit(1).toFlow()
    }

}

@Module
class MongoAppClientModule {
    @Provides
    fun provide(): MongoAppClient {
        return MongoAppClient()
    }
}