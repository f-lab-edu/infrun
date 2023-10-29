package com.flab.infrun.member.application.facade

import com.flab.infrun.common.annotation.ApplicationService
import com.flab.infrun.member.application.data.LoginData
import com.flab.infrun.member.application.processor.MemberLoginProcessorKt
import com.flab.infrun.member.application.processor.MemberSignupProcessorKt

@ApplicationService
class MemberManager(
    private val memberSignupProcessor: MemberSignupProcessorKt,
    private val memberLoginProcessor: MemberLoginProcessorKt,
) {

    fun signup(command: MemberSignupProcessorKt.Command) {
        memberSignupProcessor.execute(command)
    }

    fun login(command: MemberLoginProcessorKt.Command): LoginData {
        return memberLoginProcessor.execute(command)
    }
}
