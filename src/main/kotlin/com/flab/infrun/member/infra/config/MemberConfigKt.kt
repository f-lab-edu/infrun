package com.flab.infrun.member.infra.config

import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.domain.MemberVerifierKt
import com.flab.infrun.member.infra.MemberVerifierKtImpl
import com.flab.infrun.member.infra.persistence.MemberRepositoryImpl
import com.flab.infrun.member.infra.persistence.jpa.MemberKtJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberConfigKt {

    @Bean
    fun memberKtRepository(memberJpaRepository: MemberKtJpaRepository): MemberRepositoryKt {
        return MemberRepositoryImpl(memberJpaRepository)
    }

    @Bean
    fun memberVerifierKt(memberJpaRepository: MemberKtJpaRepository): MemberVerifierKt {
        return MemberVerifierKtImpl(memberJpaRepository)
    }
}
