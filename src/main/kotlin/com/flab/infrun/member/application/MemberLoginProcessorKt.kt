package com.flab.infrun.member.application

import com.flab.infrun.member.application.data.LoginData
import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.domain.exception.NotFoundMemberExceptionKt
import com.flab.infrun.member.domain.exception.NotMatchPasswordException
import com.flab.infrun.member.infra.jwt.TokenProviderKt
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils

class MemberLoginProcessorKt(
    private val memberRepository: MemberRepositoryKt,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProviderKt,
    private val authenticationMangerBuilder: AuthenticationManagerBuilder,
) {
    fun execute(command: Command): LoginData {
        verifyCommand(command)

        val authenticate = authenticationMangerBuilder.`object`.authenticate(
            UsernamePasswordAuthenticationToken(
                command.email,
                command.password
            )
        )

        SecurityContextHolder.getContext().authentication = authenticate

        return LoginData(tokenProvider.generateToken(authenticate))
    }

    private fun verifyCommand(command: Command) {
        if (!StringUtils.hasText(command.email)) {
            throw NotFoundMemberExceptionKt()
        }

        val member = (memberRepository.findByEmail(command.email)
            ?: throw NotFoundMemberExceptionKt())

        if (!StringUtils.hasText(command.password) || !passwordEncoder.matches(
                command.password,
                member.password
            )
        ) {
            throw NotMatchPasswordException()
        }
    }

    data class Command(
        val email: String,
        val password: String,
    )
}
