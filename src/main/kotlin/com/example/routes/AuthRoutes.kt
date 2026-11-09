// Lab 10, Step 4: Create Auth Routes
package com.example.routes

import com.example.auth.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)

fun Route.authRoutes() {
    post("/api/auth/login") {
        val req = call.receive<LoginRequest>()
        val user = UserRepository.findByUsername(req.username)
        if (user == null || !UserRepository.validatePassword(user, req.password)) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
            return@post
        }
        call.respond(LoginResponse(generateToken(user)))
    }
}