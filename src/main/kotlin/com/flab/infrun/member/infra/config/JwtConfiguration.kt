package com.flab.infrun.member.infra.config

import com.flab.infrun.member.infra.jwt.TokenProviderKt
import com.flab.infrun.member.infra.properties.JwtProperties
import com.flab.infrun.member.infra.security.handler.RestAuthenticationEntryPoint
import com.flab.infrun.member.infra.security.handler.TokenAccessDeniedHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfiguration {

    @Bean
    fun tokenAccessDeniedHandler(
        handlerExceptionResolver: HandlerExceptionResolver,
    ): AccessDeniedHandler {
        return TokenAccessDeniedHandler(handlerExceptionResolver)
    }

    @Bean
    fun restAuthenticationEntryPoint(
        handlerExceptionResolver: HandlerExceptionResolver,
    ): RestAuthenticationEntryPoint {
        return RestAuthenticationEntryPoint(handlerExceptionResolver)
    }

    @Bean
    fun tokenProviderKt(
        jwtProperties: JwtProperties,
    ): TokenProviderKt {
        return TokenProviderKt(jwtProperties = jwtProperties)
    }
}
