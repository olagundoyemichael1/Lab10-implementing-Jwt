// Lab 10, Step 7: Wire Everything Together
package com.example

import com.example.auth.configureJwt
import com.example.routes.authRoutes
import com.example.routes.taskRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

fun Application.module() {
    install(ContentNegotiation) { json() }
    configureJwt()
    routing {
        authRoutes()
        taskRoutes()
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") { module() }.start(wait = true)
}