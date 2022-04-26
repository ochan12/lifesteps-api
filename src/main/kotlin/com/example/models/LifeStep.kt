package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class LifeStep(
    val name: String,
    val type: StepType,
    val description: String? = "",
    val photos: Array<String>? = emptyArray(),
    val initialTime: Long? = 0,
    val endTime: Long? = 0,
    val place: Place? = null,
    val projects: Array<Project>
) {

    data class Builder(
        var name: String? = null,
        var type: StepType? = null,
        var description: String? = null,
        var photos: Array<String>? = null,
        var initialTime: Long? = null,
        var endTime: Long? = null,
        var place: Place? = null,
        var projects: Array<Project>? = null
    ) {
        fun setName(name: String) = apply { this.name = name }
        fun setType(type: StepType) = apply { this.type = type }
        fun setDescription(description: String) = apply { this.description = description }
        fun setPhotos(photos: Array<String>) = apply { this.photos = photos }
        fun setInitialTime(initialTime: Long) = apply { this.initialTime = initialTime }
        fun setEndTime(endTime: Long) = apply { this.endTime = endTime }
        fun setPlace(place: Place) = apply { this.place = place }
        fun setProjects(projects: Array<Project>) = apply { this.projects = projects }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LifeStep

        if (name != other.name) return false
        if (type != other.type) return false
        if (description != other.description) return false
        if (photos != null) {
            if (other.photos == null) return false
            if (!photos.contentEquals(other.photos)) return false
        } else if (other.photos != null) return false
        if (initialTime != other.initialTime) return false
        if (endTime != other.endTime) return false
        if (place != other.place) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (photos?.contentHashCode() ?: 0)
        result = 31 * result + (initialTime?.hashCode() ?: 0)
        result = 31 * result + (endTime?.hashCode() ?: 0)
        result = 31 * result + (place?.hashCode() ?: 0)
        return result
    }
}
