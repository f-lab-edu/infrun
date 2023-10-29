package com.flab.infrun.member.infra.config

import com.flab.infrun.member.domain.MemberVerifierKt
import com.flab.infrun.member.infra.persistence.jpa.MemberKtJpaRepository
import com.flab.infrun.member.infra.service.MemberVerifierKtImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberDomainServiceConfiguration {

    @Bean
    fun memberVerifierKt(memberJpaRepository: MemberKtJpaRepository): MemberVerifierKt {
        return MemberVerifierKtImpl(memberJpaRepository)
    }
}
