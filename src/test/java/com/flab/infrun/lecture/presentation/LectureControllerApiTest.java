package com.flab.infrun.lecture.presentation;

import com.flab.infrun.lecture.presentation.request.LectureDetailRequest;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LectureControllerApiTest {

    @Test
    void 강의_등록() {

        //todo- Request 작성 - file 관련 처리
//        final LectureRegisterRequest request = 강의등록요청_생성();
//
//        RestAssured.given()
//            .log().all()
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//            .body(request)
//            .when()
//            .post("/lecture")
//            .then()
//            .log().all().extract();

    }


    private static LectureRegisterRequest 강의등록요청_생성() {
        final String name = "스프링 기본 강의";
        int price = 54000;
        String introduce = "스프링 핵심 코어 강의 입니다.";
        List<LectureDetailRequest> lectureDetailRequest = List.of(
            new LectureDetailRequest("1챕터", "스프링 핵심 원리"),
            new LectureDetailRequest("1챕터", "스프링 핵심 동작"),
            new LectureDetailRequest("2챕터", "스프링 기능 첫번째"),
            new LectureDetailRequest("2챕터", "스프링 기능 두번째"),
            new LectureDetailRequest("3챕터", "스프링 최종장")
        );

        return new LectureRegisterRequest(name, price, introduce, lectureDetailRequest);
    }
}
