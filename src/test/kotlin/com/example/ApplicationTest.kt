package com.example


import com.example.models.LifeStep
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.config.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.junit.Before


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
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        client.get("/life/places").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().isNotEmpty())
        }
    }

    @Test
    fun testEducation() = testApplication {
        environment {
            config = ApplicationConfig("application-test.conf")
        }
        client.get("/life/education").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().isEmpty())
        }
    }

}