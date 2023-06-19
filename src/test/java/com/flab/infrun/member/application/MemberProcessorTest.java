package com.flab.infrun.member.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

final class MemberProcessorTest {

    private MemberRepository repository;
    private MemberProcessor processor;

    @BeforeEach
    void setUp() {
        repository = new StubMemberRepository();
        processor = new MemberProcessor(repository, new FakePasswordEncoder());
    }

    @Test
    @DisplayName("회원가입 시 이메일이 중복인 경우 예외가 발생한다")
    void register_duplicateEmailException() {
        String nickname = "userA";
        String email = "test@test.com";
        String password = "password";

        repository.save(Member.of(nickname, email, password));

        SignupCommand command = new SignupCommand("userB", "test@test.com", "password");

        assertThatThrownBy(() -> processor.register(command))
            .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("회원가입 시 닉네임이 중복인 경우 예외가 발생한다")
    void register_duplicateNicknameException() {
        String nickname = "nickname";
        String email = "userA@test.com";
        String password = "password";

        repository.save(Member.of(nickname, email, password));

        SignupCommand command = new SignupCommand("nickname", "userB@test.com", "password");

        assertThatThrownBy(() -> processor.register(command))
            .isInstanceOf(DuplicatedNicknameException.class);
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

        @Override
        public boolean existsByNickname(final String nickname) {
            return persistence.values().stream()
                .anyMatch(member -> member.getNickname().equals(nickname));
        }
    }

    private static class FakePasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(final CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
            return Objects.equals(rawPassword.toString(), encodedPassword);
        }
    }
}