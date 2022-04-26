package com.example

import com.example.di.DaggerApplicationComponent
import com.example.di.DaggerTestComponent
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.example.plugins.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import org.litote.kmongo.json


class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello there, this is Mateo's backend!", bodyAsText())
        }
    }

    @Test
    fun testPlaces() = testApplication {
        client.get("/life/places").apply {
            assertEquals(HttpStatusCode.OK, status)
            println("Mateo")
            println(bodyAsText())
            assertTrue(bodyAsText().length > 0)
        }
    }

}