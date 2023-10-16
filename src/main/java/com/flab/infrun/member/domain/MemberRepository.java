package com.flab.infrun.member.domain;

public interface MemberRepository {

    Member save(Member member);

    Member findByEmail(String email);

    Member findById(Long id);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
