package com.example.database

import com.example.models.GeoPosition
import com.example.models.LifeStep
import com.example.models.Place
import com.example.models.StepType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.Locale
import javax.inject.Inject

class TestRemoteData @Inject constructor() : DataSource {

    val steps = arrayListOf<LifeStep>()

    init {
        val cordoba = Place(
            "Córdoba",
            GeoPosition(lat = (-31.4135).toLong(), lon = (-64.18105).toLong()),
            Locale.IsoCountryCode.valueOf("es-AR")
        )
        val sweden = Place(
            "Stockholm",
            GeoPosition(lat = (59.33258).toLong(), lon = (18.0649).toLong()),
            Locale.IsoCountryCode.valueOf("sv-SE")
        )
        val qbit = LifeStep("Qbit", StepType.JOB, "First job", arrayOf(), 0, 1, cordoba)
        val rd = LifeStep("Reputación digital", StepType.JOB, "BigData", arrayOf(), 2, 3, cordoba)
        val cruncho = LifeStep("Cruncho", StepType.JOB, "Senior Full Stack Developer", arrayOf(), 4, 6, sweden)
        steps.add(qbit)
        steps.add(rd)
        steps.add(cruncho)

        val rugby = LifeStep("Rugby", StepType.HOBBY, "Sport", arrayOf(), 0, 1, cordoba)
        steps.add(rugby)

        val barcelona = LifeStep("Barcelona", StepType.PLACE, "First country in my long trip", arrayOf(), 0, 1)
        steps.add(barcelona)
    }

    override fun getSteps(): Flow<LifeStep> {

        return flow {
            steps.map { s -> emit(s) }
        }
    }

    override fun getStepsByType(type: StepType): Flow<LifeStep> {
        println(steps.toString())
        return flow {
            steps.filter { s -> s.type == type }.map { s -> emit(s) }
        }
    }

    override suspend fun postStep(step: LifeStep): String {
        steps.add(step)
        return steps.size.toString()
    }
}