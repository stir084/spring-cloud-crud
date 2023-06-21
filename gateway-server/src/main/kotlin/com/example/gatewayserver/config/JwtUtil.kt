package com.example.gatewayserver.config

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.expiration}")
    private val expiration: Long
) {
/*    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }*/

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            // Invalid JWT signature
        } catch (ex: MalformedJwtException) {
            // Invalid JWT token
        } catch (ex: ExpiredJwtException) {
            // Expired JWT token
        } catch (ex: UnsupportedJwtException) {
            // Unsupported JWT token
        } catch (ex: IllegalArgumentException) {
            // JWT claims string is empty
        }
        return false
    }

    fun getUsernameFromToken(token: String): String? {
        val claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
        return claims.subject
    }
}