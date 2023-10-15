package com.flab.infrun.member.infrastructure

import com.flab.infrun.member.domain.MemberVerifier
import com.flab.infrun.member.domain.exception.DuplicatedEmailException
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException
import com.flab.infrun.member.domain.exception.InvalidPasswordException
import com.flab.infrun.member.infrastructure.persistence.MemberKtJpaRepository
import lombok.RequiredArgsConstructor
import org.springframework.util.StringUtils
import java.util.regex.Pattern

@RequiredArgsConstructor
class MemberVerifierImpl(
    private val memberJpaRepository: MemberKtJpaRepository
) : MemberVerifier {

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
        if (!StringUtils.hasText(password)
            || !Pattern.matches(
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,16}$",
                password
            )
        ) {
            throw InvalidPasswordException()
        }
    }
}
