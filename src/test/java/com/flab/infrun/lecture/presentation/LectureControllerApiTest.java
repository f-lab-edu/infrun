package com.flab.infrun.lecture.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.lecture.presentation.request.LectureDetailRequest;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import com.flab.infrun.member.presentation.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LectureControllerApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static final String MEMBER_URI = "/members";

    @LocalServerPort
    private int port;


    @BeforeAll
    static void setupData(@Autowired DataSource dataSource) {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("/db/h2/data.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setup() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
        }
    }

    private String loginSuccess_returnToken(LoginRequest loginRequest) {
        ExtractableResponse<Response> response = RestAssured.given()
            .log()
            .all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .post(MEMBER_URI + "/login")
            .then()
            .log()
            .all()
            .extract();
        return response.body().jsonPath().getString("data");
    }

    private LectureRegisterRequest lectureRequestCreate() {
        final String name = "스프링 기본 강의";
        int price = 54000;
        int lectureLevel = 2;
        String skill = "JAVA";
        String introduce = "스프링 핵심 코어 강의 입니다.";
        long userId = 1L;
        List<LectureDetailRequest> lectureDetailRequest = List.of(
//            new LectureDetailRequest("1챕터", "스프링 핵심 원리", "test.txt"),
//            new LectureDetailRequest("1챕터", "스프링 핵심 동작", ""),
//            new LectureDetailRequest("2챕터", "스프링 기능 첫번째", ""),
//            new LectureDetailRequest("2챕터", "스프링 기능 두번째", "test1.txt"),
//            new LectureDetailRequest("3챕터", "스프링 최종장", "")
        );

        return new LectureRegisterRequest(name, price, lectureLevel, skill, introduce,
            lectureDetailRequest);
    }

    private MockMultipartHttpServletRequestBuilder createMultiPartRequest()
        throws JsonProcessingException {
        MockMultipartFile multipartFile1 = new MockMultipartFile("file", "test.txt",
            "text/plain", "test file".getBytes(
            StandardCharsets.UTF_8));
        MockMultipartFile multipartFile2 = new MockMultipartFile("file", "test1.txt",
            "text/plain", "test file2".getBytes(
            StandardCharsets.UTF_8));
        LectureRegisterRequest lecture = lectureRequestCreate();
        String lectureJson = mapper.writeValueAsString(lecture);
        MockMultipartFile mockLecture = new MockMultipartFile("lecture", "lecture",
            "application/json", lectureJson.getBytes(StandardCharsets.UTF_8));

        return multipart("/lecture")
            .file(multipartFile1)
            .file(multipartFile2)
            .file(mockLecture);
    }

    private LoginRequest createTeacherLoginRequest() {
        return new LoginRequest("teacher1@test.com", "1234Qwer!");
    }
}
