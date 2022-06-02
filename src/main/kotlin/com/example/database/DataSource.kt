package com.example.database

import com.example.models.*
import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun getSteps(): Flow<LifeStep>
    fun getStepsByType(type: StepType): Flow<LifeStep>
    suspend fun postStep(step: LifeStep): String
    suspend fun getContactData(): Flow<Contact?>
    suspend fun getPersonalData(): Flow<Person?>
    suspend fun getProjects(projects: List<String>): Flow<Project?>
    suspend fun getResources(resources: List<String>): Flow<Resource?>
}