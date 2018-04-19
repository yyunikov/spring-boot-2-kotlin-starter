package com.yunikov.api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class JWTClient(val issuer: String, private val secret: String) {

    fun generate(userId: String, userEmail: String): String {
        return generate(userId, userEmail, arrayOf("user"))
    }

    private fun generate(id: String, email: String, roles: Array<String>): String {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(id)
                .setIssuedAt(Date())
                .setIssuer(issuer)
                .addClaims(mapOf(Pair("email", email), Pair("roles", roles)))
                .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
                .compact()
    }

    fun verify(token: String): Jws<Claims> {
        return Jwts.parser()
                .setSigningKey(secret.toByteArray())
                .requireIssuer(issuer)
                .parseClaimsJws(token)
    }
}