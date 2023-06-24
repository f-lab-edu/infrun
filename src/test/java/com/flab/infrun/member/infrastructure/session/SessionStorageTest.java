package com.flab.infrun.member.infrastructure.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.member.domain.exception.InvalidAuthenticationException;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SessionStorageTest {

    private static final String validEmail = "valid@email.com";
    private static final String validToken = "token";
    private final SessionStorage sessionStorage = new SessionStorage();

    private static Stream<Arguments> provideInvalidEmailOrToken() {
        return Stream.of(
            Arguments.of(validEmail, "wrongToken"),
            Arguments.of("wrongEmail", validToken));
    }

    @BeforeEach
    void setUp() {
        sessionStorage.store(validEmail, validToken);
    }

    @Test
    @DisplayName("이메일을 넘기면 세션을 반환한다")
    void giveEmail_createSession() {
        final String email = "userB@test.com";
        final String token = "token";

        final Session session = sessionStorage.store(email, token);

        assertThat(session).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmailOrToken")
    @DisplayName("이메일이나 토큰이 유효하지 않으면 예외가 발생한다")
    void invalidEmailOrToken_throwException(final String email, final String token) {
        assertThatThrownBy(() -> sessionStorage.verifyToken(email, token))
            .isInstanceOf(InvalidAuthenticationException.class);
    }
}
