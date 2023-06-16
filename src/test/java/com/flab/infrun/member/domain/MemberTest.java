package com.flab.infrun.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


final class MemberTest {

    private MemberRepository memberRepository;
    private Member member;

    @BeforeEach
    void setUp() {
        memberRepository = new StubMemberRepository();
        member = MemberFixture.createMember();
    }

    @Test
    @DisplayName("회원이 저장되면 저장된 유저를 반환한다")
    void register_success() {
        Member registeredMember = memberRepository.save(member);

        assertThat(registeredMember.getId()).isEqualTo(1L);
        assertThat(registeredMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(registeredMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(registeredMember.getPassword()).isEqualTo(member.getPassword());
    }

    @Test
    @DisplayName("이메일이 중복되면 true를 반환한다")
    void register_duplicateEmail() {
        memberRepository.save(member);

        boolean result = memberRepository.existsByEmail(MemberFixture.EMAIL);

        assertThat(result).isTrue();
    }


    private static final class StubMemberRepository implements MemberRepository {

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
    }

    private static final class MemberFixture {

        public static final String NICKNAME = "test";
        public static final String EMAIL = "test@test.com";
        public static final String PASSWORD = "12341234";

        public static Member createMember() {
            return Member.of(NICKNAME, EMAIL, PASSWORD);
        }
    }
}