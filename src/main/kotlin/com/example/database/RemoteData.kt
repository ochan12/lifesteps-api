package com.example.database

import com.example.models.LifeStep
import com.example.models.StepType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient
import javax.inject.Inject

class RemoteData @Inject constructor(private val client: MongoAppClient) : DataSource {
    override fun getSteps(): Flow<LifeStep> {
        return client.getSteps()
    }

    override fun getStepsByType(type: StepType): Flow<LifeStep> {
        return client.getStepsByType(type)
    }
}