package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Project(
    val name: String,
    val description: String,
    val resources: List<String>?,
    val startTime: Long?,
    val endTime: Long?,
    val company: String?,
    val url: String?,
    val userId: String?,
    val _id: Id<Project> = newId(),
) {
    data class Builder(
        private var name: String,
        private var description: String,
        private var resources: List<String>? = emptyList(),
        private var startTime: Long? = 0,
        private var endTime: Long? = 0,
        private var company: String? = "",
        private var url: String? = "",
        private var userId: String? = "",
        private var key: Id<Project> = newId(),
    ) {
        fun setUrl(url: String) = apply { this.url = url }
        fun setUserId(userId: String) = apply { this.userId = userId }

        fun setResources(resources: List<String>?) = apply { this.resources = resources }

        fun setCompany(company: String?) = apply { this.company = company }

        fun build() = Project(
            this.name,
            this.description,
            this.resources,
            this.startTime,
            this.endTime,
            this.company,
            this.url,
            this.userId,
            this.key
        )
    }
}