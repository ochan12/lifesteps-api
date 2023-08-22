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
    val allProjects = mutableListOf<Project>()
    val allResources = mutableListOf<Resource>()
    val environment = Environment()
    val user = User.Builder().setUsername("riggoch")
        .setPassword(
            Hasher.sha256(
                environment.getVariable("PASSWORD"),
                environment.getVariable("AUTH_SALT")
            )
        )
        .setEmail("mateochando@gmail.com").build()

    init {


        val dataInitializer = DataInitializer(userId = user._id.toString())
        contact = dataInitializer.contact
        person = dataInitializer.person
        steps.addAll(
            listOf(
                dataInitializer.buildQbit(dataInitializer.qbitProjects.map { p -> p._id.toString() }),
                dataInitializer.buildRd(dataInitializer.rdProjects.map { p -> p._id.toString() }),
                dataInitializer.buildCruncho(dataInitializer.crunchoProjects.map { p -> p._id.toString() }),
            )
        )
        allProjects.addAll(dataInitializer.qbitProjects)
        allProjects.addAll(dataInitializer.rdProjects)
        allProjects.addAll(dataInitializer.crunchoProjects)
        allResources.addAll(dataInitializer.resources)
    }

    override fun getSteps(userId: String): Flow<LifeStep> {
        return flow {
            steps.map { s -> emit(s) }
        }
    }

    override fun getStepsByType(type: StepType, userId: String): Flow<LifeStep> {
        return flow {
            steps.filter { s -> s.type == type }.map { s -> emit(s) }
        }
    }

    override suspend fun postStep(step: LifeStep): String {
        steps.add(step)
        return steps.size.toString()
    }

    override suspend fun getContactData(userId: String): Flow<Contact?> {
        return flow { contact }
    }

    override suspend fun getPersonalData(userId: String): Flow<Person?> {
        return flow { person }
    }

    override suspend fun getProjects(projects: List<String>): Flow<Project?> {
        return flow { allProjects.filter { project -> projects.contains(project._id.toString()) }}
    }

    override suspend fun getResources(resources: List<String>): Flow<Resource?> {
        return flow { allResources.filter { resource -> resources.contains(resource._id.toString()) }}
    }
}

@Module
class TestRemoteDataModule {
    @Provides
    fun provide(): TestRemoteData {
        return TestRemoteData()
    }
}