package com.example.plugins

import io.github.cdimascio.dotenv.dotenv

class Environment {
    fun getVariable(name: String, fallbackValue: String = ""): String =
        (System.getenv(name) ?: dotenv()[name] ?: fallbackValue)
}