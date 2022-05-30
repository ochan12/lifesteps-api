package com.example.models
import kotlinx.serialization.Serializable

@Serializable
data class Resource(val name: String, val logo: String, val url: String)
