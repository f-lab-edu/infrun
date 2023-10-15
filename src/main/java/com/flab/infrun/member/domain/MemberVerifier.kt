package com.flab.infrun.member.domain

interface MemberVerifier {
    fun verifyNickname(nickname: String)
    fun verifyEmail(email: String)
    fun verifyPassword(password: String)
}
