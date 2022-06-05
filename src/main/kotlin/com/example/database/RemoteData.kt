package com.example.database

import com.example.models.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient
import javax.inject.Inject

class RemoteData @Inject constructor(private val client: MongoAppClient) : DataSource {
    override fun getSteps(userId: String): Flow<LifeStep> {
        return client.getSteps(userId)
    }

    override fun getStepsByType(type: StepType, userId: String): Flow<LifeStep> {
        return client.getStepsByType(type, userId)
    }

    override suspend fun postStep(step: LifeStep): String {
        if (step.validate() == LifeStep.VALID_STEP.VALID)
            return client.createStep(step)
        throw Exception(LifeStep.VALID_STEP.INVALID.name)
    }

    override suspend fun getContactData(userId: String): Flow<Contact?> {
        return client.getContactData(userId)
    }

    override suspend fun getPersonalData(userId: String): Flow<Person?> {
        return client.getPersonalData(userId)
    }

    override suspend fun getProjects(projects: List<String>): Flow<Project?> {
        return client.getProjects(projects)
    }

    override suspend fun getResources(resources: List<String>): Flow<Resource?> {
        return client.getResources(resources)
    }

}

@Module
class RemoteDataModule {
    @Provides
    fun provide(
        mongoAppClient: MongoAppClient
    ): RemoteData {
        return RemoteData(mongoAppClient)
    }
}