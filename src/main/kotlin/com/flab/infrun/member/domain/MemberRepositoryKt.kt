package com.flab.infrun.member.domain

interface MemberRepositoryKt {
    fun save(member: MemberKt): MemberKt
    fun findById(id: Long): MemberKt?
    fun findByEmail(email: String): MemberKt?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
