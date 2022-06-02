package com.example.models
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Resource(val name: String, val logo: String, val url: String, @BsonId val key: Id<Project> = newId(),)
