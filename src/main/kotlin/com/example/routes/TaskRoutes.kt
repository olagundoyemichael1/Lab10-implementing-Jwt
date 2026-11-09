// Lab 10, Step 5: Protect Task Routes
package com.example.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*

fun Route.taskRoutes() {
    authenticate("auth-jwt") {
        get("/api/tasks") {
            call.respond(mapOf("message" to "You are authenticated!", "user" to call.principal<JWTPrincipal>()?.getClaim("username", String::class)))
        }
    }
}