package com.flab.infrun.member.infra.config

import com.flab.infrun.member.application.facade.MemberManager
import com.flab.infrun.member.application.processor.MemberLoginProcessorKt
import com.flab.infrun.member.application.processor.MemberSignupProcessorKt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberFacadeConfiguration {

    @Bean
    fun memberManager(
        memberSignupProcessor: MemberSignupProcessorKt,
        memberLoginProcessor: MemberLoginProcessorKt,
    ): MemberManager {
        return MemberManager(
            memberSignupProcessor = memberSignupProcessor,
            memberLoginProcessor = memberLoginProcessor,
        )
    }
}
