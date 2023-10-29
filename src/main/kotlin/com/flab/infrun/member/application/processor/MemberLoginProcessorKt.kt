package com.flab.infrun.member.application.processor

import com.flab.infrun.common.annotation.ApplicationService
import com.flab.infrun.member.application.data.LoginData
import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.domain.exception.NotFoundMemberExceptionKt
import com.flab.infrun.member.domain.exception.NotMatchPasswordException
import com.flab.infrun.member.infra.jwt.TokenProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils

@ApplicationService
class MemberLoginProcessorKt(
    private val memberRepository: MemberRepositoryKt,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
    private val authenticationMangerBuilder: AuthenticationManagerBuilder,
) {
    @Transactional
    fun execute(command: Command): LoginData {
        verifyCommand(command)

        return LoginData("token")
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
