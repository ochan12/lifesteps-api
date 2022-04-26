package com.example

import com.example.di.DaggerApplicationComponent
import com.example.di.DaggerTestComponent
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerApplicationComponent.create()
    install(ContentNegotiation){
        json()
    }
    configureSecurity()
    configureRouting(appComponent.remoteData())
}

fun Application.testModule() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerTestComponent.create()
    install(ContentNegotiation){
        json()
    }
    configureSecurity()
    configureRouting(appComponent.remoteData())

}





