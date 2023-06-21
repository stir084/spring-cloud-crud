package com.example.microservicepost


import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.*
import javax.xml.bind.DatatypeConverter




@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration}")
    private val expiration: Duration
) {


    fun extractUsernameFromToken(token: String): String {
        val token = extractTokenFromHeader(token)
        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .parseClaimsJws(token)
            .getBody()
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            parseToken(token)
            true
        } catch (ex: JwtException) {
            false
        }
    }

    fun extractTokenFromHeader(header: String?): String {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7)
        }
        return ""
    }

    private fun parseToken(token: String): Claims? {
        val parsedToken = Jwts.parser()
            .setSigningKey(secretKey.toByteArray(StandardCharsets.UTF_8))
            .parseClaimsJws(token)

        return parsedToken.body
    }


}