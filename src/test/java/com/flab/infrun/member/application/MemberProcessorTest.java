package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class MemberProcessorTest {

    private MemberRepository repository;
    private MemberProcessor processor;

    @BeforeEach
    void setUp() {
        repository = new StubMemberRepository();
        processor = new MemberProcessor(repository);
    }

    @Test
    @DisplayName("회원가입 시 이메일이 중복인 경우 예외가 발생한다")
    void register_duplicateEmailException() {
        String nickname = "nickname";
        String email = "test@test.com";
        String password = "password";

        repository.save(Member.of(nickname, email, password));

        SignupCommand command = new SignupCommand("nickname", "test@test.com", "password");

        Assertions.assertThatThrownBy(() -> processor.register(command))
            .isInstanceOf(DuplicatedEmailException.class);
    }

    private static final class StubMemberRepository implements MemberRepository {

        private final Map<Long, Member> persistence = new ConcurrentHashMap<>();
        private Long sequence = 0L;

        @Override
        public Member save(final Member member) {
            persistence.put(++sequence, member);
            return member;
        }

        @Override
        public boolean existsByEmail(final String email) {
            return persistence.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
        }
    }
}