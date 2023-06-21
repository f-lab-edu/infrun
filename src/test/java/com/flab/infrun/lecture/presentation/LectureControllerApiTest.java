package com.flab.infrun.lecture.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.lecture.presentation.request.LectureDetailRequest;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LectureControllerApiTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Multipart 요청 파일과 request 객체가 함께 들어 온 경우 정상적인 처리 확인")
    void lectureRegisterTest() throws Exception {

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

        mockMvc.perform(multipart("/lecture")
                .file(multipartFile1)
                .file(multipartFile2)
                .file(mockLecture)
            )
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andReturn();
    }


    private static LectureRegisterRequest lectureRequestCreate() {

        final String name = "스프링 기본 강의";
        int price = 54000;
        String introduce = "스프링 핵심 코어 강의 입니다.";
        List<LectureDetailRequest> lectureDetailRequest = List.of(
            new LectureDetailRequest("1챕터", "스프링 핵심 원리", "test.txt"),
            new LectureDetailRequest("1챕터", "스프링 핵심 동작", ""),
            new LectureDetailRequest("2챕터", "스프링 기능 첫번째", ""),
            new LectureDetailRequest("2챕터", "스프링 기능 두번째", "test1.txt"),
            new LectureDetailRequest("3챕터", "스프링 최종장", "")
        );

        return new LectureRegisterRequest(name, price, introduce, lectureDetailRequest);
    }


}



