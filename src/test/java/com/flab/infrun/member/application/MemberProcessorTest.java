package com.flab.infrun.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.member.application.command.LoginCommand;
import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import com.flab.infrun.member.infrastructure.session.SessionStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class MemberProcessorTest {

    private MemberProcessor processor;

    private static void setUpMember(final MemberRepository repository) {
        final String nickname = "userA";
        final String email = "test@test.com";
        final String password = "password";
        repository.save(Member.of(nickname, email, password));
    }

    @BeforeEach
    void setUp() {
        final MemberRepository repository = new StubMemberRepository();
        processor = new MemberProcessor(
            repository,
            new FakePasswordEncoder(),
            new SessionStorage());

        setUpMember(repository);
    }

    @Test
    @DisplayName("회원가입 시 이메일이 중복인 경우 예외가 발생한다")
    void register_duplicateEmailException() {
        final SignupCommand command = new SignupCommand("userB", "test@test.com", "password");

        assertThatThrownBy(() -> processor.register(command))
            .isInstanceOf(DuplicatedEmailException.class);
    }

    @Test
    @DisplayName("회원가입 시 닉네임이 중복인 경우 예외가 발생한다")
    void register_duplicateNicknameException() {
        final SignupCommand command = new SignupCommand("userA", "userB@test.com", "password");

        assertThatThrownBy(() -> processor.register(command))
            .isInstanceOf(DuplicatedNicknameException.class);
    }

    @Test
    @DisplayName("이메일과 비밀번호가 일치하면 토큰을 반환한다")
    void loginSuccess_returnToken() {
        final LoginCommand command = new LoginCommand("test@test.com", "password");

        final String token = processor.login(command);

        assertThat(token).isNotNull();
    }

}