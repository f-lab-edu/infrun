package com.flab.infrun.common.config

import com.flab.infrun.member.infra.jwt.TokenProvider
import com.flab.infrun.member.infra.security.filter.TokenAuthenticationFilter
import com.flab.infrun.member.infra.security.handler.RestAuthenticationEntryPoint
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfiguration(
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
    private val tokenAccessDeniedHandler: AccessDeniedHandler,
    private val tokenProvider: TokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(tokenAccessDeniedHandler)
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
            }
            .headers { it -> it.frameOptions { it.sameOrigin() } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/members/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                TokenAuthenticationFilter(
                    tokenProvider = tokenProvider,
                    handlerExceptionResolver = handlerExceptionResolver,
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
