package com.example.plugins

import com.example.database.UserSource
import io.ktor.server.auth.*
import io.ktor.server.application.*
import io.ktor.util.*

fun <T : UserSource> Application.configureSecurity(userSource: T, environment: Environment = Environment()) {

    val digestFunction = getDigestFunction("SHA-256") { environment.getVariable("AUTH_SALT") }
    val hashedUserTable = UserHashedTableAuth(
        table = mapOf(
            "riggoch" to digestFunction(environment.getVariable("PASSWORD")),
        ),
        digester = digestFunction
    )

    authentication {
        basic {
            validate { credentials ->
                hashedUserTable.authenticate(credentials)
            }
        }
    }
}
