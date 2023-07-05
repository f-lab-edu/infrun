package com.flab.infrun.member.domain;

import java.util.Optional;

public interface MemberRepository {

    Member save(final Member member);

    boolean existsByEmail(final String email);

    boolean existsByNickname(final String nickname);

    Optional<Member> findByEmail(final String email);
}
