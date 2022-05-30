package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val name: String,
    val description: String,
    val resources: Array<Resource>?,
    val startTime: Long,
    val endTime: Long,
    val company: String?,
    val url: String?
)