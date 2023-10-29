package com.flab.infrun.member.`interface`

import com.flab.infrun.member.application.facade.MemberManager
import com.flab.infrun.member.`interface`.request.MemberLoginRequest
import com.flab.infrun.member.`interface`.request.MemberSignupRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberControllerKt(
    private val memberManager: MemberManager,
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun signup(@RequestBody request: MemberSignupRequest) {
        memberManager.signup(request.toCommand())
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    fun login(@RequestBody request: MemberLoginRequest) {
        memberManager.login(request.toCommand())
    }
}
