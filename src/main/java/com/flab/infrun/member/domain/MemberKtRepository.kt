package com.flab.infrun.member.domain

interface MemberKtRepository {
    fun save(member: MemberKt): MemberKt
    fun findByEmail(email: String): MemberKt
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
