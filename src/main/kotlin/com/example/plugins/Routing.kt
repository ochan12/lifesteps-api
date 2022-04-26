package com.example.plugins

import com.example.database.RemoteData
import com.example.database.RemoteData_Factory
import com.example.models.LifeStep
import com.example.models.StepType
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.rmi.Remote
import javax.inject.Inject

fun Application.configureRouting(remoteData: RemoteData) {
    routing {
        get("/") {
            call.respondText("Hello there, this is Mateo's backend!")
        }
        lifeRouting(remoteData)
        contactRouting()
    }
}

fun Route.lifeRouting(remoteData: RemoteData) {
    route("/life") {
        get("/jobs") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.JOB).toList()
                call.respondText(steps.joinToString(("\n")) { s -> s.toString() })
            }
        }
        get("/education") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.EDUCATION).toList()
                call.respondText(steps.joinToString(("\n")) { s -> s.toString() })
            }
        }
        get("/places") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.PLACE).toList()
                call.respondText(steps.joinToString(("\n")) { s -> s.toString() })
            }
        }
        get("/hobbies") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.HOBBY).toList()
                call.respondText(steps.joinToString(("\n")) { s -> s.toString() })
            }
        }
        post ("/step") {
            runBlocking {
                val step = call.receive<LifeStep>()
                val id = remoteData.postStep(step)
                call.respond(id)
            }
        }
    }
}

fun Route.contactRouting() {
    route("/contact") {
        get("/full") {
            call.respondText("This is my job experience")
        }
        get("/email") {
            call.respondText("Here is where I studied")
        }
        get("/github") {
            call.respondText("This is where I travelled")
        }
        get("/linkedin") {
            call.respondText("What I like doing in my free time")
        }
    }
}
