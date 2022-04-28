package com.example.plugins

import com.example.database.DataSource
import com.example.models.Contact
import com.example.models.LifeStep
import com.example.models.Person
import com.example.models.StepType
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun <T : DataSource> Application.configureRouting(remoteData: T) {
    routing {
        get("/") {
            call.respondText("Hello there, this is Mateo's backend!")
        }
        lifeRouting(remoteData)
        contactRouting(remoteData)
        personRouting(remoteData)
    }
}

fun <T : DataSource> Route.lifeRouting(remoteData: T) {
    route("/life") {
        get("/jobs") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.JOB).toList()
                call.respond(steps)
            }
        }
        get("/education") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.EDUCATION).toList()
                call.respond(steps)
            }
        }
        get("/places") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.PLACE).toList()
                call.respond(steps)
            }
        }
        get("/hobbies") {
            runBlocking {
                val steps = remoteData.getStepsByType(StepType.HOBBY).toList()
                call.respond(steps)
            }
        }
        post("/step") {
            runBlocking {
                val step = call.receive<LifeStep>()
                val id = remoteData.postStep(step)
                call.respond(id)
            }
        }
    }
}

fun <T : DataSource> Route.contactRouting(remoteData: T) {
    route("/contact") {
        get("/email") {
            runBlocking {
                val contact = remoteData.getContactData().map { d -> d?.email }
                call.respond(contact.first() ?: "")
            }
        }
        get("/repository") {
            runBlocking {
                val contact = remoteData.getContactData().map { d -> d?.repository }
                call.respond(contact.first() ?: "")
            }
        }
        get("/linkedin") {
            runBlocking {
                val contact = remoteData.getContactData().map { d -> d?.linkedIn }
                call.respond(contact.first() ?: "")
            }
        }
        get("/phone") {
            runBlocking {
                val contact = remoteData.getContactData().map { d -> d?.phone }
                call.respond(contact.first() ?: "")
            }
        }
        get("/") {
            runBlocking {
                val contact = remoteData.getContactData().toList()
                call.respond(contact.first() ?: "")
            }
        }
    }
}

fun <T : DataSource> Route.personRouting(remoteData: T) {
    route("/person") {
        get("/data") {
            call.respond(flow<Person> { remoteData.getPersonalData() })
        }
    }
}
