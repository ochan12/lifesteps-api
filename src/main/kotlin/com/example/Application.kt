package com.example

import com.example.di.DaggerApplicationComponent
import com.example.di.DaggerTestComponent
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.github.cdimascio.dotenv.Dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.cors.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerApplicationComponent.create()
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowCredentials = true
    }
    install(ContentNegotiation){
        json(
            Json { serializersModule = IdKotlinXSerializationModule  },
            contentType = ContentType.Application.Json
        )
    }
    configureSecurity(appComponent.userRemoteData())
    configureRouting(appComponent.remoteData())
}

fun Application.testModule() {
    val dotenv = Dotenv.configure()
    val appComponent = DaggerTestComponent.create()
    install(ContentNegotiation){
        json()
    }
    configureSecurity(appComponent.userRemoteData())
    configureRouting(appComponent.remoteData())

}





