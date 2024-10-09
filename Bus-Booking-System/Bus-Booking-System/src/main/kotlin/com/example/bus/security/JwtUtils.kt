package com.example.bus.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)  // HS256 encryption key

    // Generate token with phone number as the subject
    fun generateToken(phone: String): String {
        return Jwts.builder()
            .setSubject(phone)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10-hour expiration
            .signWith(secretKey)
            .compact()
    }

    // Validate the token
    fun validateToken(token: String): Boolean {
        return try {
            val claims = extractAllClaims(token)
            !isTokenExpired(claims)
        } catch (e: Exception) {
            false
        }
    }

    // Extract phone number from the token
    fun extractPhone(token: String): String {
        return extractAllClaims(token).subject
    }

    // Extract all claims from the token
    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    // Check if token is expired
    private fun isTokenExpired(claims: Claims): Boolean {
        return claims.expiration.before(Date())
    }
}
