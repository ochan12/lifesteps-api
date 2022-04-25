package com.example.database

import com.example.models.LifeStep
import com.example.models.StepType
import kotlinx.coroutines.flow.Flow


interface DataSource {
    fun getSteps(): Flow<LifeStep>
    fun getStepsByType(type: StepType): Flow<LifeStep>
}