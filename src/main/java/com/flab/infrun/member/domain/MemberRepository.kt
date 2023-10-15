package com.flab.infrun.member.domain

internal interface MemberRepository {
    fun save(member: Member): Member
    fun findByEmail(email: String): Member
    fun findById(id: Long): Member
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
