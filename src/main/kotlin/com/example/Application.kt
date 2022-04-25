package com.example

import com.example.di.DaggerApplicationComponent
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.github.cdimascio.dotenv.Dotenv
import javax.inject.Inject

fun main() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerApplicationComponent.create()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSecurity()
        configureRouting(appComponent.remoteData())
    }.start(wait = true)
}





