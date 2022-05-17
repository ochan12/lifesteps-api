package com.example.plugins

import com.example.database.UserSource
import com.example.models.User
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.*
import java.util.logging.Logger
import javax.swing.text.DefaultEditorKit

fun <T : UserSource> Application.configureSecurity(userSource: T) {

    val digestFunction = getDigestFunction("SHA-256") { System.getenv("AUTH_SALT") ?: dotenv()["AUTH_SALT"] }
    val hashedUserTable = UserHashedTableAuth(
        table = mapOf(
            "jetbrains" to digestFunction("foobar"),
            "admin" to digestFunction("password")
        ),
        digester = digestFunction
    )

    authentication {
        provider("userToken") {
            authenticate {
                println(it.call.request.headers["Auth"])
                it.call.request.headers["Auth"]?.let { token -> userSource.getUser(token) }
            }

        }

    }
}
