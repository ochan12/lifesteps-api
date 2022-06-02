package com.example.plugins

import com.example.database.DataSource
import com.example.models.LifeStep
import com.example.models.Person
import com.example.models.StepType
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun <T : DataSource> Application.configureRouting(remoteData: T) {
    routing {
        get("/") {
            call.respondText("Hello there, this is Mateo's backend!")
        }
        authenticate() {
            lifeRouting(remoteData)
            contactRouting(remoteData)
            personRouting(remoteData)
        }
    }
}

fun <T : DataSource> Route.lifeRouting(remoteData: T) {
    route("/life") {
        get("/{stepType}") {
            try {
                val stepType = when (call.parameters["stepType"]) {
                    "jobs" -> StepType.JOB
                    "education" -> StepType.EDUCATION
                    "trips" -> StepType.TRAVEL
                    "hobbies" -> StepType.HOBBY
                    else -> throw Exception("Not a valid method")
                }
                runBlocking {
                    val steps = remoteData.getStepsByType(stepType).toList()
                    call.respond(steps)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, e.message!!)
            }
        }
        post("/step") {
            runBlocking {
                try {
                    call.receiveOrNull<LifeStep>()?.let {
                        val id = remoteData.postStep(it)
                        call.respond(id)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, "Invalid LifeStep: ${e.message}")
                }
            }
        }
    }
    route("/project") {
        get {
            val projectId = call.request.queryParameters["ids"]
            if (projectId != null) {
                runBlocking {
                    val projects = remoteData.getProjects(projects = projectId.split(","))
                    call.respond(projects)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters *ids* missing")
            }
        }
    }
    route("/resource") {
        get {
            val projectId = call.request.queryParameters["ids"]
            if (projectId != null) {
                runBlocking {
                    val projects = remoteData.getResources(resources = projectId.split(","))
                    call.respond(projects)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Query parameters *ids* missing")
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
        get {
            call.respond(flow<Person> { remoteData.getPersonalData() })
        }
    }
}
