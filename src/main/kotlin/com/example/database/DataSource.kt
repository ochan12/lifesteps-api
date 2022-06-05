package com.example.database

import com.example.models.*
import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun getSteps(userId: String): Flow<LifeStep>
    fun getStepsByType(type: StepType, userId: String): Flow<LifeStep>
    suspend fun postStep(step: LifeStep): String
    suspend fun getContactData(userId: String): Flow<Contact?>
    suspend fun getPersonalData(userId: String): Flow<Person?>
    suspend fun getProjects(projects: List<String>): Flow<Project?>
    suspend fun getResources(resources: List<String>): Flow<Resource?>
}