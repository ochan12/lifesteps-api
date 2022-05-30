package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val name: String,
    val description: String,
    val resources: Array<Resource>?,
    val startTime: Long?,
    val endTime: Long?,
    val company: String?,
    val url: String?
) {
    data class Builder(
        private var name: String,
        private var description: String,
        private var resources: Array<Resource>? = emptyArray(),
        private var startTime: Long? = 0,
        private var endTime: Long? = 0,
        private var company: String? = "",
        private var url: String? = ""
    ) {
        fun setUrl(url: String) = apply { this.url = url }

        fun setResources(resources: Array<Resource>?) = apply { this.resources = resources }

        fun setCompany(company: String?) = apply { this.company = company }

        fun build() = Project(
            this.name,
            this.description,
            this.resources,
            this.startTime,
            this.endTime,
            this.company,
            this.url
        )
    }
}