package com.flab.infrun.member.application

import com.flab.infrun.common.annotation.ApplicationService
import com.flab.infrun.member.domain.MemberKt
import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.domain.MemberVerifierKt
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

@ApplicationService
class MemberSignupProcessorKt(
    private val memberRepositoryKt: MemberRepositoryKt,
    private val passwordEncoder: PasswordEncoder,
    private val memberVerifierKt: MemberVerifierKt,
) {

    @Transactional
    fun execute(command: Command) {
        verifyCommand(command)

        val member = MemberKt(
            nickname = command.nickname,
            email = command.email,
            password = passwordEncoder.encode(command.password),
        )

        memberRepositoryKt.save(member)
    }

    private fun verifyCommand(command: Command) {
        memberVerifierKt.verifyNickname(command.nickname)
        memberVerifierKt.verifyEmail(command.email)
        memberVerifierKt.verifyPassword(command.password)
    }

    data class Command(
        val nickname: String,
        val email: String,
        val password: String,
    )
}
