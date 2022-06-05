package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class LifeStepWithProjects(
    val name: String,
    val type: StepType,
    val description: String? = "",
    val photos: List<String>? = emptyList(),
    val initialTime: Long? = 0,
    val endTime: Long? = 0,
    val place: Place? = null,
    val projects: List<Project>? = emptyList(),
    val userId: String? = "",
) {

    data class Builder(
        val lifeStep: LifeStep, val projectsParameter: List<Project>?
    ) {
        fun build() =
            LifeStepWithProjects(
                lifeStep.name,
                lifeStep.type,
                lifeStep.description,
                lifeStep.photos,
                lifeStep.initialTime,
                lifeStep.endTime,
                lifeStep.place,
                projectsParameter,
                lifeStep.userId,
            )
    }


}
