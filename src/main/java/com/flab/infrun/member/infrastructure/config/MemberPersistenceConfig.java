package com.flab.infrun.member.infrastructure.config;

import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import com.flab.infrun.member.infrastructure.persistence.MemberRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberPersistenceConfig {

    @Bean
    public MemberRepositoryAdapter memberRepositoryAdapter(
        final MemberJpaRepository memberJpaRepository
    ) {
        return new MemberRepositoryAdapter(memberJpaRepository);
    }
}
