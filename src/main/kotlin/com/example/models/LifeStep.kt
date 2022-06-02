package com.example.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.id.MongoId
import org.litote.kmongo.newId

@Serializable
data class LifeStep(
    val name: String,
    val type: StepType,
    val description: String? = "",
    val photos: List<String>? = emptyList(),
    val initialTime: Long? = 0,
    val endTime: Long? = 0,
    val place: Place? = null,
    var projects: List<String>? = emptyList(),
    val userId: String? = "",
    @Contextual @SerialName("_id") val _id: Id<LifeStep> = newId(),
) {
    data class Builder(
        private var name: String? = null,
        private var type: StepType? = null,
        private var description: String? = null,
        private var photos: List<String>? = emptyList(),
        private var initialTime: Long? = 0,
        private var endTime: Long? = 0,
        private var place: Place? = null,
        private var projects: List<String>? = emptyList(),
        private var userId: String? = null
    ) {
        fun setName(name: String) = apply { this.name = name }
        fun setType(type: StepType) = apply { this.type = type }
        fun setDescription(description: String) = apply { this.description = description }
        fun setPhotos(photos: List<String>) = apply { this.photos = photos }
        fun setInitialTime(initialTime: Long) = apply { this.initialTime = initialTime }
        fun setEndTime(endTime: Long) = apply { this.endTime = endTime }
        fun setPlace(place: Place) = apply { this.place = place }
        fun setProjects(projects: List<String>?) = apply { this.projects = projects }

        fun setUserId(userId: String) =apply { this.userId = userId }

        fun build(): LifeStep = LifeStep(
            this.name!!,
            this.type!!,
            this.description,
            this.photos,
            this.initialTime,
            this.endTime,
            this.place,
            this.projects,
            this.userId
        )
    }

    fun validate(): VALID_STEP {
        if (name.isEmpty() || type.name.isEmpty())
            return VALID_STEP.INVALID
        if (initialTime == null || initialTime <= 0)
            return VALID_STEP.INVALID
        if (endTime == null || endTime <= 0 || endTime < initialTime)
            return VALID_STEP.INVALID
        return VALID_STEP.VALID
    }

    enum class VALID_STEP {
        VALID,
        INVALID
    }
}
