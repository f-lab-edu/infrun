package com.flab.infrun.member.infra.jwt

import com.flab.infrun.common.domain.AuthenticatedUser
import com.flab.infrun.common.domain.Role
import com.flab.infrun.member.infra.properties.JwtProperties
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.time.Instant
import java.util.*

class TokenProvider(
    private val jwtProperties: JwtProperties,
) {

    private val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
    private var key: Key = Keys.hmacShaKeyFor(keyBytes)

    fun createToken(
        userId: Long,
        email: String,
        roles: List<Role>,
    ): String {
        val now = Instant.now()

        return encode(
            AuthenticatedUser(
                jti = UUID.randomUUID().toString(),
                userId = userId,
                email = email,
                roles = roles,
                issuer = jwtProperties.issuer,
                issuedAt = now,
                expiry = now.plusSeconds(jwtProperties.accessTokenExpirySecs),
            )
        )
    }

    private fun encode(authenticatedUser: AuthenticatedUser): String {
        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject(authenticatedUser.userId.toString())
            .setIssuer(jwtProperties.issuer)
            .setIssuedAt(Date.from(Instant.now()))
            .claim("email", authenticatedUser.email)
            .claim("roles", authenticatedUser.roles)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(Date.from(authenticatedUser.expiry))
            .compact()
    }

    fun decode(token: String): AuthenticatedUser {
        val claims = try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(jwtProperties.issuer)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            e.claims
        } catch (e: Exception) {
            throw e
        }

        val jti = claims.id ?: throw MalformedJwtException("invalid jwt")
        val userId = claims.subject.toLong() ?: throw MalformedJwtException("invalid userId")
        val email =
            claims.get("email", String::class.java) ?: throw MalformedJwtException("invalid email")
        val roles = claims.get("roles", List::class.java).map { Role.valueOf(it as String) }
            ?: throw MalformedJwtException("invalid roles")
        val issuer = claims.issuer ?: throw MalformedJwtException("invalid issuer")
        val issuedAt = claims.issuedAt ?: throw MalformedJwtException("invalid issuedAt")
        val expiry = claims.expiration ?: throw MalformedJwtException("invalid expiry")

        return AuthenticatedUser(
            jti = jti,
            userId = userId,
            email = email,
            roles = roles,
            issuer = issuer,
            issuedAt = issuedAt.toInstant(),
            expiry = expiry.toInstant(),
        )
    }
}
