package com.flab.infrun.member.domain;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class StubMemberRepository implements MemberRepository {

    private final Map<Long, Member> persistence = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Member save(final Member member) {
        persistence.put(++sequence, member);
        member.assignId(sequence);
        return member;
    }

    @Override
    public boolean existsByEmail(final String email) {
        return persistence.values().stream()
            .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return persistence.values().stream()
            .anyMatch(member -> member.getNickname().equals(nickname));
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        return persistence.values().stream()
            .filter(member -> Objects.equals(member.getEmail(), email))
            .findAny();
    }
}
