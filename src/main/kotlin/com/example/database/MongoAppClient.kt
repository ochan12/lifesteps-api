package com.example.database

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.example.models.Contact
import com.example.models.LifeStep
import com.example.models.Person
import com.example.models.StepType
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MongoAppClient @Inject constructor() {
    private val user = System.getenv("MONGO_USER") ?: dotenv()["MONGO_USER"]
    private val pass = System.getenv("MONGO_PASS") ?: dotenv()["MONGO_PASS"]
    private val host = System.getenv("MONGO_HOST") ?: dotenv()["MONGO_HOST"]
    private val db = System.getenv("MONGO_DB") ?: dotenv()["MONGO_DB"]
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

}