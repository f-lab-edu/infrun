package com.flab.infrun.member.infra.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors

@Component
class TokenProviderKt(
    private val userDetailsService: UserDetailsService,
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.token-validity-in-seconds}")
    private var tokenValidityInMilliseconds: Long = 0,
) : InitializingBean {

    private var key: Key? = null

    init {
        this.tokenValidityInMilliseconds *= 1000
    }

    override fun afterPropertiesSet() {
        val keyBytes = Decoders.BASE64.decode(secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(authentication: Authentication): String {
        val authorities = getAuthoritiesToString(authentication)
        val validity = validityDate

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(validity)
            .compact()
    }

    private fun getAuthoritiesToString(authentication: Authentication): String {
        return authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
    }

    private val validityDate: Date
        get() {
            val now = Date().time
            return Date(now + tokenValidityInMilliseconds)
        }

    fun getAuthentication(token: String?): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val userDetails = userDetailsService.loadUserByUsername(claims.subject)
        val authorities = Arrays.stream(
            claims[AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
            .map { role: String? -> SimpleGrantedAuthority(role) }
            .toList()

        return UsernamePasswordAuthenticationToken(userDetails, token, authorities)
    }

    fun validateToken(token: String?): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        private const val AUTHORITIES_KEY = "auth"
    }
}
