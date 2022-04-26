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
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class TestRemoteData @Inject constructor() : DataSource {

    val steps = arrayListOf<LifeStep>()

    init {
        val cordoba = Place(
            "Córdoba",
            GeoPosition(lat = (-31.4135).toLong(), lon = (-64.18105).toLong()),
            Locale("es", "AR").isO3Country
        )

        val sweden = Place(
            "Stockholm",
            GeoPosition(lat = (59.33258).toLong(), lon = (18.0649).toLong()),
            Locale("sv", "SE").isO3Country
        )
        val qbit =
            LifeStep.Builder().setName("Qbit").setType(StepType.JOB).setDescription("First job").setPlace(cordoba)
                .build()
        val rd = LifeStep.Builder().setName("Reputación digital").setType(StepType.JOB).setDescription("BigData")
            .setPlace(cordoba).build()


        val cruncho =
            LifeStep.Builder().setName("Cruncho").setType(StepType.JOB).setDescription("Senior Full Stack Developer")
                .setPlace(sweden).build()
        steps.add(qbit)
        steps.add(rd)
        steps.add(cruncho)

        val rugby =
            LifeStep.Builder().setName("Rugby").setType(StepType.HOBBY).setDescription("Sport").setPlace(cordoba)
                .build()
        steps.add(rugby)

        val barcelona = LifeStep.Builder().setName("Barcelona").setType(StepType.PLACE)
            .setDescription("First country in my long trip").build()
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