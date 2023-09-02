package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import com.flab.infrun.lecture.domain.StubLectureRepository;
import com.flab.infrun.member.domain.StubMemberRepository;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;


class LectureCommandProcessorTest {

    private LectureCommandProcessor processor;
    private final StubLectureRepository lectureRepository = new StubLectureRepository();
    private final StubMemberRepository memberRepository = new StubMemberRepository();

    @BeforeEach
    void setup() {
        processor = new LectureCommandProcessor(
            //todo- utils stub refactor
            lectureRepository,
            null,
            null,
            memberRepository,
            null
        );
    }

    @Test
    @DisplayName("강의 파일 이름 중복 불가능 exception 테스트")
    void validateLectureFile() {

        MockMultipartFile multipartFile1 = setupMultiPartTextFile("fileDup1", "test.txt",
            "test file");
        MockMultipartFile multipartFile2 = setupMultiPartTextFile("fileDup1", "test1.txt",
            "test file2");
        MockMultipartFile multipartFile3 = setupMultiPartTextFile("fileDup2", "test1.txt",
            "test file2");

        assertThatThrownBy(() -> processor.validateLectureFile(
            List.of(multipartFile1, multipartFile2, multipartFile3))).isInstanceOf(
            DuplicateLectureFileNameException.class);
    }

    private MockMultipartFile setupMultiPartTextFile(String fileName, String originFileName,
        String content) {
        return new MockMultipartFile(fileName, originFileName,
            "text/plain", content.getBytes(
            StandardCharsets.UTF_8));
    }
}
