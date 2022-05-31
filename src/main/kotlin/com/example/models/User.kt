package com.example.models

import io.ktor.server.auth.*
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class User(
    val username: String?,
    val password: String?,
    val email: String?,
    @BsonId val key: Id<User> = newId(),
    ): Principal {

    data class Builder(
        private var username: String? = null,
        private var password: String? = null,
        private var email: String? = null
    ) {
        fun setUsername(username: String) = apply { this.username = username }
        fun setPassword(password: String) = apply { this.password = password }
        fun setEmail(email: String) = apply { this.email = email }
        fun build() = User(this.username, this.password, this.email)
    }

}