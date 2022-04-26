package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val name: String,
    val description: String,
    val technologies: Array<Technology>?,
    val startTime: Long,
    val endTime: Long,
    val company: String
)