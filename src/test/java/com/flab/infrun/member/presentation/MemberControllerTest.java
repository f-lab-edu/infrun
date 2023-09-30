package com.flab.infrun.member.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
final class MemberControllerTest {

    private static final String MEMBER_URI = "/members";

    @LocalServerPort
    private int port;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    public static SignupRequest createCorrectSignupRequest() {
        return new SignupRequest("nickname", "test@test.com", "1234Qwer!");
    }

    public static SignupRequest createIncorrectSignupRequest() {
        return new SignupRequest("", "", "");
    }

    public static LoginRequest createCorrectLoginRequest() {
        return new LoginRequest("test@test.com", "1234Qwer!");
    }

    private static ExtractableResponse<Response> signup(final SignupRequest request) {
        return RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .post(MEMBER_URI)
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
        memberJpaRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원가입이 성공하면 201 상태코드와 유저 ID를 반환한다")
    void register_success() {
        var response = signup(createCorrectSignupRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getLong("data")).isNotNull();
    }

    @Test
    @DisplayName("회원가입 시 입력값이 잘못되면 400 상태코드와 에러 메시지를 반환한다")
    void register_incorrect_request() {
        var response = signup(createIncorrectSignupRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("errorCode")).isNotNull();
    }

    @Test
    @DisplayName("회원가입 시 닉네임 또는 이메일이 중복이면 400 상태코드와 에러 메시지를 반환한다")
    void register_duplicateEmail() {
        signup(createCorrectSignupRequest());
        var response = signup(createCorrectSignupRequest());

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.jsonPath().getString("errorCode")).isNotNull();
    }

    @Test
    @DisplayName("로그인 시 JWT 토큰을 반환한다")
    void loginSuccess_returnToken() {
        signup(createCorrectSignupRequest());
        var response = RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createCorrectLoginRequest())
            .post(MEMBER_URI + "/login")
            .then()
            .log()
            .all()
            .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("data")).isNotNull();
    }
}