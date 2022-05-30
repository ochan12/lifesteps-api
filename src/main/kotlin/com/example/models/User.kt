package com.example.models

import io.ktor.server.auth.*

data class User(
    val userId: String? = "",
    val token: String? = ""
): Principal {

    data class Builder(
        private var userId: String? = null,
        private var token: String? = null
    ) {
        fun setUserId(userId: String) = apply { this.userId = userId }
        fun setToken(token: String) = apply { this.token = token }
        fun build() = User(this.userId, this.token)
    }

}