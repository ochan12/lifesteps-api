package com.example.database

import com.example.models.*
import com.example.plugins.Environment
import com.example.utils.Hasher
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
        val environment = Environment()
        val user = User.Builder().setUsername("riggoch")
            .setPassword(
                Hasher.sha256(
                    environment.getVariable("PASSWORD"),
                    environment.getVariable("AUTH_SALT")
                )
            )
            .setEmail("mateochando@gmail.com").build()

        val dataInitializer = DataInitializer(userId = user.key.toString())
        contact = dataInitializer.contact
        person = dataInitializer.person
        steps.addAll(
            listOf(
                dataInitializer.buildQbit(dataInitializer.qbitProjects.map { p -> p.key.toString() }),
                dataInitializer.builRd(dataInitializer.rdProjects.map { p -> p.key.toString() }),
                dataInitializer.builCruncho(dataInitializer.crunchoProjects.map { p -> p.key.toString() }),
            )
        )
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