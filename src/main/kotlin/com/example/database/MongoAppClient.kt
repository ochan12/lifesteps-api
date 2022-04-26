package com.example.database

import com.example.models.LifeStep
import com.example.models.StepType
import dagger.Provides
import dagger.Module
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.reactivestreams.*
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.eq
import java.util.logging.Logger
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

    init {
        val lifeStep = LifeStep("Qbit", StepType.JOB)
        runBlocking {
            client.getDatabase(db).getCollection<LifeStep>().deleteMany(LifeStep::type eq StepType.JOB)
            client.getDatabase(db).getCollection<LifeStep>().insertOne(lifeStep)
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


    internal class QueryBuilder {

    }
}