package com.flab.infrun.member.infra.persistence.jpa

import com.flab.infrun.member.domain.MemberKt
import org.springframework.data.jpa.repository.JpaRepository

interface MemberKtJpaRepository : JpaRepository<MemberKt, Long> {

    fun save(member: MemberKt): MemberKt
    fun findByEmail(email: String): MemberKt?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}