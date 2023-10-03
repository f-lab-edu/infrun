package com.flab.infrun.member.application;

import static com.flab.infrun.member.domain.MemberFixture.aMemberFixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.common.IntegrationTest;
import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import com.flab.infrun.member.domain.exception.InvalidPasswordException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

final class MemberSignupProcessorTest extends IntegrationTest {

    @Autowired
    private MemberSignupProcessor sut;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입이 성공한다")
    void signup() {
        final var command = new SignupCommand("userA", "userA@naver.com",
            "Qwer1234!");

        sut.execute(command);

        assertThat(memberRepository.findByEmail(command.email())).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCommand")
    @DisplayName("회원가입 시 입력이 올바르지 않은 경우 예외가 발생한다")
    void signup_withInvalidCommand(final SignupCommand command) {
        final var member = aMemberFixture()
            .nickname("userA")
            .email("userA@naver.com")
            .build();
        memberRepository.save(member);

        assertThatThrownBy(() -> sut.execute(command))
            .isInstanceOfAny(
                DuplicatedEmailException.class,
                DuplicatedNicknameException.class,
                InvalidPasswordException.class);
    }

    private static Stream<Arguments> provideInvalidCommand() {
        return Stream.of(
            Arguments.of(new SignupCommand("userA", "userB@naver.com", "password")),
            Arguments.of(new SignupCommand("userB", "userA@naver.com", "password")),
            Arguments.of(new SignupCommand("userC", "userC@naver.com", null)),
            Arguments.of(new SignupCommand("userD", "userD@naver.com", "password")),
            Arguments.of(new SignupCommand("userE", null, "Qwer1234!")),
            Arguments.of(new SignupCommand(null, "userF@naver.com", "Qwer1234!"))
        );
    }
}