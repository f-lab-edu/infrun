package com.flab.infrun.member.infra.config

import com.flab.infrun.member.application.MemberLoginProcessorKt
import com.flab.infrun.member.application.MemberSignupProcessorKt
import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.domain.MemberVerifierKt
import com.flab.infrun.member.infra.jwt.TokenProviderKt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class MemberApplicationServiceConfiguration {

    @Bean
    fun memberLoginProcessorKt(
        memberRepository: MemberRepositoryKt,
        passwordEncoder: PasswordEncoder,
        tokenProvider: TokenProviderKt,
        authenticationManagerBuilder: AuthenticationManagerBuilder,
    ): MemberLoginProcessorKt {
        return MemberLoginProcessorKt(
            memberRepository = memberRepository,
            passwordEncoder = passwordEncoder,
            tokenProvider = tokenProvider,
            authenticationMangerBuilder = authenticationManagerBuilder,
        )
    }

    @Bean
    fun memberSignupProcessorKt(
        memberRepositoryKt: MemberRepositoryKt,
        passwordEncoder: PasswordEncoder,
        memberVerifierKt: MemberVerifierKt,
    ): MemberSignupProcessorKt {
        return MemberSignupProcessorKt(
            memberRepositoryKt = memberRepositoryKt,
            passwordEncoder = passwordEncoder,
            memberVerifierKt = memberVerifierKt,
        )
    }
}
