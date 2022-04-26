package com.example.models

import kotlinx.serialization.Serializable


@Serializable
data class LifeStep (
    val name: String,
    val type: StepType,
    val description: String? = "",
    val photos: Array<String>? = emptyArray(),
    val initialTime: Long? = 0,
    val endTime: Long? = 0,
    val place: Place? = null,
    val projects: Array<Project>
) {
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
