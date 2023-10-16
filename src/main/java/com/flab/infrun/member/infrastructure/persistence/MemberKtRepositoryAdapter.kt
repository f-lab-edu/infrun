package com.flab.infrun.member.infrastructure.persistence

import com.flab.infrun.member.domain.MemberKt
import com.flab.infrun.member.domain.MemberKtRepository
import com.flab.infrun.member.domain.exception.NotFoundMemberException
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class MemberKtRepositoryAdapter(
    private val jpaRepository: MemberKtJpaRepository
) : MemberKtRepository {

    override fun save(member: MemberKt): MemberKt {
        return jpaRepository.save(member)
    }

    override fun existsByEmail(email: String): Boolean {
        return jpaRepository.existsByEmail(email)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return jpaRepository.existsByNickname(nickname)
    }

    override fun findByEmail(email: String): MemberKt {
        return jpaRepository.findByEmail(email) ?: throw NotFoundMemberException()
    }

    fun findById(id: Long): MemberKt {
        return jpaRepository.findById(id)
            .orElseThrow { NotFoundMemberException() }
    }
}
