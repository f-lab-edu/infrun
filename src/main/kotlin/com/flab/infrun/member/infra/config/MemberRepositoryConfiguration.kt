package com.flab.infrun.member.infra.config

import com.flab.infrun.member.domain.MemberRepositoryKt
import com.flab.infrun.member.infra.persistence.MemberRepositoryImpl
import com.flab.infrun.member.infra.persistence.jpa.MemberKtJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MemberRepositoryConfiguration {

    @Bean
    fun memberKtRepository(memberJpaRepository: MemberKtJpaRepository): MemberRepositoryKt {
        return MemberRepositoryImpl(memberJpaRepository)
    }
}
