package com.flab.infrun.member.infra.service

import com.flab.infrun.member.domain.MemberVerifierKt
import com.flab.infrun.member.domain.exception.DuplicatedEmailException
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException
import com.flab.infrun.member.domain.exception.InvalidPasswordException
import com.flab.infrun.member.infra.persistence.jpa.MemberKtJpaRepository
import org.springframework.util.StringUtils
import java.util.regex.Pattern

class MemberVerifierKtImpl(
    private val memberJpaRepository: MemberKtJpaRepository
) : MemberVerifierKt {

    override fun verifyNickname(nickname: String) {
        if (!StringUtils.hasText(nickname) || memberJpaRepository.existsByNickname(nickname)) {
            throw DuplicatedNicknameException()
        }
    }

    override fun verifyEmail(email: String) {
        if (!StringUtils.hasText(email) || memberJpaRepository.existsByEmail(email)) {
            throw DuplicatedEmailException()
        }
    }

    override fun verifyPassword(password: String) {
        if (!StringUtils.hasText(password) || !Pattern.matches(
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,16}$",
                password
            )
        ) {
            throw InvalidPasswordException()
        }
    }
}
