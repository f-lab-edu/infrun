package com.flab.infrun.member.domain;

public interface MemberRepository {

    Member save(final Member member);

    boolean existsByEmail(final String email);

    boolean existsByNickname(final String nickname);

    Member findByEmail(final String email);

    Member findById(final Long id);
}
