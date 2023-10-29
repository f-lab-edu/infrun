package com.flab.infrun.member.infra.security.filter

import com.flab.infrun.member.infra.jwt.TokenProviderKt
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import java.time.Instant

class TokenAuthenticationFilter(
    private val tokenProvider: TokenProviderKt,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        resolveToken(request)?.let { token ->
            val authenticatedUser = try {
                tokenProvider.decode(token)
            } catch (e: Exception) {
                handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    e,
                )
                return
            }

            val now = Instant.now()
            if (authenticatedUser.expiry.toEpochMilli() < now.toEpochMilli()) {
                handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    AccessDeniedException("expired token")
                )
            }

            val authorities: Collection<GrantedAuthority> = authenticatedUser.roles
                .map { SimpleGrantedAuthority("ROLE_$it") }
                .toList()
            val authentication =
                UsernamePasswordAuthenticationToken(authenticatedUser, token, authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)

    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}
