package com.flab.infrun.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.common.IntegrationTest;
import com.flab.infrun.member.application.command.LoginCommand;
import com.flab.infrun.member.domain.MemberFixture;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import com.flab.infrun.member.domain.exception.NotMatchPasswordException;
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

final class MemberLoginProcessorTest extends IntegrationTest {

    @Autowired
    private MemberLoginProcessor sut;
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("이메일과 비밀번호가 일치하면 토큰을 반환한다")
    void login_returnToken() {
        final var member = MemberFixture.aMemberFixture()
            .email("test@test.com")
            .password(passwordEncoder.encode("password"))
            .build();
        memberRepository.saveAndFlush(member);
        final var command = new LoginCommand("test@test.com", "password");

        final var result = sut.execute(command);

        assertThat(result).isNotNull();
        assertThat(result.token()).isNotBlank();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCommand")
    @DisplayName("이메일 또는 비밀번호가 일치하지 않으면 예외가 발생한다")
    void login_withInvalidCommand(final LoginCommand command) {
        final var member = MemberFixture.aMemberFixture()
            .email("test@test.com")
            .password(passwordEncoder.encode("password"))
            .build();
        memberRepository.saveAndFlush(member);

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOfAny(NotFoundMemberException.class, NotMatchPasswordException.class);
    }

    private static Stream<Arguments> provideInvalidCommand() {
        return Stream.of(
            Arguments.of(new LoginCommand(null, "password")),
            Arguments.of(new LoginCommand("wrong_email@test.com", "password")),
            Arguments.of(new LoginCommand("test@test.com", null)),
            Arguments.of(new LoginCommand("test@test.com", "wrong_password"))
        );
    }
}