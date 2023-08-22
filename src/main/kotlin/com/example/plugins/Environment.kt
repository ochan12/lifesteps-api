package com.example.plugins

import io.github.cdimascio.dotenv.dotenv

class Environment {
    val env = dotenv {
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }
    fun getVariable(name: String, fallbackValue: String = ""): String =
        (System.getenv(name) ?: env[name] ?: fallbackValue)
}
