package com.example.database

import com.example.models.Contact
import com.example.models.LifeStep
import com.example.models.Person
import com.example.models.StepType
import dagger.Module
import dagger.Provides
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

    override suspend fun postStep(step: LifeStep): String {
        if(step.validate() == LifeStep.VALID_STEP.VALID)
            return client.createStep(step)
        throw Error(LifeStep.VALID_STEP.INVALID.name)
    }

    override suspend fun getContactData(): Flow<Contact?> {
        return client.getContactData()
    }

    override suspend fun getPersonalData(): Flow<Person?> {
        return client.getPersonalData()
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