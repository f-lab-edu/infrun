package com.flab.infrun.member.infrastructure.config;

import com.flab.infrun.member.domain.MemberVerifier;
import com.flab.infrun.member.infrastructure.MemberVerifierImpl;
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import com.flab.infrun.member.infrastructure.persistence.MemberRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public MemberRepositoryAdapter memberRepositoryAdapter(
        final MemberJpaRepository memberJpaRepository
    ) {
        return new MemberRepositoryAdapter(memberJpaRepository);
    }

    @Bean
    public MemberVerifier memberVerifier(
        final MemberJpaRepository memberJpaRepository
    ) {
        return new MemberVerifierImpl(memberJpaRepository);
    }
}
