package com.example.plugins

import com.example.database.DataSource
import com.example.models.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import kotlinx.coroutines.awaitAll
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
                    val userId = call.principal<User>()?._id.toString()
                    val steps = remoteData.getStepsByType(stepType, userId).map {
                        val projects: List<Project>
                        if (it.projects != null) {
                            projects = remoteData.getProjects(it.projects!!).toList() as List<Project>
                        } else {
                            projects = emptyList()
                        }
                        LifeStepWithProjects.Builder(it, projects).build()
                    }.toList()
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
    route("/resources") {
        get {
            val projectId = call.request.queryParameters["ids"]
            if (projectId != null) {
                runBlocking {
                    val projects = remoteData.getResources(resources = projectId.split(",")).toList()
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
                val userId = call.principal<User>()?._id.toString()
                val contact = remoteData.getContactData(userId).map { d -> d?.email }
                call.respond(contact.first() ?: "")
            }
        }
        get("/repository") {
            runBlocking {
                val userId = call.principal<User>()?._id.toString()
                val contact = remoteData.getContactData(userId).map { d -> d?.repository }
                call.respond(contact.first() ?: "")
            }
        }
        get("/linkedin") {
            runBlocking {
                val userId = call.principal<User>()?._id.toString()
                val contact = remoteData.getContactData(userId).map { d -> d?.linkedIn }
                call.respond(contact.first() ?: "")
            }
        }
        get("/phone") {
            runBlocking {
                val userId = call.principal<User>()?._id.toString()
                val contact = remoteData.getContactData(userId).map { d -> d?.phone }
                call.respond(contact.first() ?: "")
            }
        }
        get("") {
            runBlocking {
                val userId = call.principal<User>()?._id.toString()
                val contact = remoteData.getContactData(userId).toList()
                if (contact.isEmpty()) call.respond(HttpStatusCode.NoContent, "No user id")
                else call.respond(HttpStatusCode.OK, contact.first() ?: "No content")
            }
        }
    }
}

fun <T : DataSource> Route.personRouting(remoteData: T) {
    route("/person") {
        get {
            val userId = call.principal<User>()?._id.toString()
            val person = remoteData.getPersonalData(userId).toList()
            if (person.isEmpty()) call.respond(HttpStatusCode.NoContent, "No user id")
            else call.respond(HttpStatusCode.OK, person.first() ?: "No content")
        }
    }
}
