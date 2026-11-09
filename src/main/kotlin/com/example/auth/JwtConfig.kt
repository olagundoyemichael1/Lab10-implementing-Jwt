// Lab 10, Step 3: Configure JWT
package com.example.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val JWT_SECRET = "my-super-secret-key"
const val JWT_ISSUER = "ktor-task-api"
const val JWT_AUDIENCE = "ktor-task-api-audience"
const val JWT_REALM = "ktor task api"

fun Application.configureJwt() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JWT_REALM
            verifier(
                JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .withAudience(JWT_AUDIENCE)
                    .withIssuer(JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}

fun generateToken(user: User): String = JWT.create()
    .withAudience(JWT_AUDIENCE)
    .withIssuer(JWT_ISSUER)
    .withClaim("username", user.username)
    .withClaim("role", user.role)
    .sign(Algorithm.HMAC256(JWT_SECRET))