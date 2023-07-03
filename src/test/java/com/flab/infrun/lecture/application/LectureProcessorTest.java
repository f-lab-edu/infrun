package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;


class LectureProcessorTest {

    private final LectureProcessor lectureProcessor = new LectureProcessor(null, null, null, null);

    @Test
    @DisplayName("강의 파일 이름 중복 불가능 exception 테스트")
    void validateLectureFile() {
        MockMultipartFile multipartFile1 = new MockMultipartFile("fileDup1", "test.txt",
            "text/plain", "test file".getBytes(
            StandardCharsets.UTF_8));
        MockMultipartFile multipartFile2 = new MockMultipartFile("fileDup1", "test1.txt",
            "text/plain", "test file2".getBytes(
            StandardCharsets.UTF_8));
        MockMultipartFile multipartFile3 = new MockMultipartFile("fileDup2", "test1.txt",
            "text/plain", "test file2".getBytes(
            StandardCharsets.UTF_8));

        assertThatThrownBy(() -> lectureProcessor.validateLectureFile(
            List.of(multipartFile1, multipartFile2, multipartFile3))).isInstanceOf(
            DuplicateLectureFileNameException.class);
    }
}
