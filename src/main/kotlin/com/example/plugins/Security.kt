package com.example.plugins

import com.example.database.UserSource
import com.example.utils.Hasher
import io.ktor.server.auth.*
import io.ktor.server.application.*
import io.ktor.util.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList

fun <T : UserSource> Application.configureSecurity(userSource: T, environment: Environment = Environment()) {

    authentication {
        basic {
            validate { credentials ->
                val user = userSource.getUser(
                    credentials.name,
                    Hasher.sha256(credentials.password, environment.getVariable("AUTH_SALT"))
                )
                if (user.count() > 0) {
                    user.first()
                } else {
                    authentication.error("Wrong password", AuthenticationFailedCause.InvalidCredentials)
                    null
                }
            }
        }
    }
}
