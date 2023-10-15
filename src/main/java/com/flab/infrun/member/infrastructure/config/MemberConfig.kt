package com.flab.infrun.member.infrastructure.config

import com.flab.infrun.member.domain.MemberVerifier
import com.flab.infrun.member.infrastructure.MemberVerifierImpl
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository
import com.flab.infrun.member.infrastructure.persistence.MemberKtJpaRepository
import com.flab.infrun.member.infrastructure.persistence.MemberKtRepositoryAdapter
import com.flab.infrun.member.infrastructure.persistence.MemberRepositoryAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberConfig {
    @Bean
    fun memberRepositoryAdapter(
        memberJpaRepository: MemberJpaRepository
    ): MemberRepositoryAdapter {
        return MemberRepositoryAdapter(memberJpaRepository)
    }

    @Bean
    fun memberVerifier(
        memberJpaRepository: MemberKtJpaRepository
    ): MemberVerifier {
        return MemberVerifierImpl(memberJpaRepository)
    }

    @Bean
    fun memberKtRepositoryAdapter(
        memberJpaRepository: MemberKtJpaRepository
    ): MemberKtRepositoryAdapter {
        return MemberKtRepositoryAdapter(memberJpaRepository)
    }
}
