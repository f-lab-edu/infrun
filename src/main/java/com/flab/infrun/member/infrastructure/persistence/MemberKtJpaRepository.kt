package com.flab.infrun.member.infrastructure.persistence

import com.flab.infrun.member.domain.MemberKt
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberKtJpaRepository : JpaRepository<MemberKt, Long> {
    fun save(member: MemberKt): MemberKt
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun findByEmail(email: String): MemberKt?
    override fun findById(id: Long): Optional<MemberKt>
}
