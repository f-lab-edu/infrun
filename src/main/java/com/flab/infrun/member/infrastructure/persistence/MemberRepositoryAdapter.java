package com.flab.infrun.member.infrastructure.persistence;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;

public class MemberRepositoryAdapter implements MemberRepository {

    private final MemberJpaRepository jpaRepository;

    public MemberRepositoryAdapter(
        final MemberJpaRepository jpaRepository
    ) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Member save(final Member member) {
        return jpaRepository.save(member);
    }

    @Override
    public boolean existsByEmail(final String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }
}
