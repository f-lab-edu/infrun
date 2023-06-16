package com.flab.infrun.member.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class MemberControllerTest {

    private static final String REGISTER_MEMBER_URI = "/members/signup";

    @LocalServerPort
    private int port;

    public static SignupRequest createCorrectRequest() {
        return new SignupRequest("nickname", "test@test.com", "password");
    }

    public static SignupRequest createIncorrectRequest() {
        return new SignupRequest("", "", "");
    }

    private static ExtractableResponse<Response> signup(final SignupRequest request) {
        return RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .post(REGISTER_MEMBER_URI)
            .then()
            .log()
            .all()
            .extract();
    }

    @BeforeEach
    void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }

    @Test
    @DisplayName("회원가입이 성공하면 201 상태코드와 유저 ID를 반환한다")
    void register_success() {
        var response = signup(createCorrectRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getLong("data")).isNotNull();
    }

    @Test
    @DisplayName("회원가입 시 입력값이 잘못되면 400 상태코드와 에러 메시지를 반환한다")
    void register_incorrect_request() {
        var response = signup(createIncorrectRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("errorCode")).isNotNull();
    }

    @Test
    @DisplayName("회원가입 시 닉네임 또는 이메일이 중복이면 400 상태코드와 에러 메시지를 반환한다")
    void register_duplicateEmail() {
        signup(createCorrectRequest());
        var response = signup(createCorrectRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("errorCode")).isNotNull();
    }
}