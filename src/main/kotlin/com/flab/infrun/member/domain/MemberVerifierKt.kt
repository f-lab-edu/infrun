package com.flab.infrun.member.domain

interface MemberVerifierKt {
    fun verifyNickname(nickname: String)
    fun verifyEmail(email: String)
    fun verifyPassword(password: String)
}
