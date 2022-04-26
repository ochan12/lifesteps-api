package com.example

import com.example.di.DaggerApplicationComponent
import com.example.di.DaggerTestComponent
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.server.application.*
import javax.inject.Inject

fun main(args: Array<String>)  = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerApplicationComponent.create()
    configureSecurity()
    configureRouting(appComponent.remoteData())
}

fun Application.testModule() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerTestComponent.create()
    configureSecurity()
    configureRouting(appComponent.remoteData())
}





