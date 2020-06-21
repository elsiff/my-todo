package me.elsiff.mytodo.auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

/**
 * Created by elsiff on 2020-06-10.
 */
@Service
class JwtSigner(
    @Value("\${jwt.secret}")
    val secret: String
) {
    val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun createJwt(username: String): String {
        return Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setSubject(username)
            .setIssuer("my-todo.elsiff.me")
            .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(15))))
            .setIssuedAt(Date.from(Instant.now()))
            .compact()
    }

    fun validateJwt(jwt: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(jwt)
    }
}