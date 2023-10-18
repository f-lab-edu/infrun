package com.flab.infrun.member.infra.persistence

import com.flab.infrun.member.domain.MemberKt
import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.infra.persistence.jpa.MemberKtJpaRepository
import org.springframework.data.repository.findByIdOrNull

class MemberRepositoryImpl(
    private val memberJpaRepository: MemberKtJpaRepository
) : MemberRepositoryKt {

    override fun save(member: MemberKt): MemberKt {
        return memberJpaRepository.save(member)
    }

    override fun findById(id: Long): MemberKt? {
        return memberJpaRepository.findByIdOrNull(id)
    }

    override fun findByEmail(email: String): MemberKt? {
        return memberJpaRepository.findByEmail(email)
    }

    override fun existsByEmail(email: String): Boolean {
        return memberJpaRepository.existsByEmail(email)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return memberJpaRepository.existsByNickname(nickname)
    }
}
