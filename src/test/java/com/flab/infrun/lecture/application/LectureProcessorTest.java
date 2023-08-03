package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureException;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;


class LectureProcessorTest {

    private LectureCommandProcessor processor;
    private final StubLectureRepository lectureRepository = new StubLectureRepository();
    private final StubMemberRepository memberRepository = new StubMemberRepository();

    @BeforeEach
    void setup() {
        processor = new LectureCommandProcessor(
            //todo- utils stub refactor
            lectureRepository,
            null,
            new StubLectureReviewRepository(),
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

    @Test
    @DisplayName("강의 리뷰 등록 실패 - lecture notfound")
    void registerLectureReviewLectureException() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(15L,
            "review content",
            setupMember());
        assertThatThrownBy(() -> processor.registerLectureReview(command)).isInstanceOf(
            NotFoundLectureException.class);
    }

    @Test
    @DisplayName("강의 리뷰 등록 실패 - member notfound")
    void registerLectureReviewMemberException() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(setupLecture(),
            "review content",
            "test@test.com");

        assertThatThrownBy(() -> processor.registerLectureReview(command)).isInstanceOf(
            NotFoundMemberException.class);
    }

    @Test
    @DisplayName("강의 리뷰 등록 성공")
    void registerLectureReview() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(setupLecture(),
            "review content",
            setupMember());

        assertDoesNotThrow(() -> processor.registerLectureReview(command));
    }

    private MockMultipartFile setupMultiPartTextFile(String fileName, String originFileName,
        String content) {
        return new MockMultipartFile(fileName, originFileName,
            "text/plain", content.getBytes(
            StandardCharsets.UTF_8));
    }

    private Long setupLecture() {
        Lecture lecture = Lecture.of("Lecture1", 20000, 1, "JAVA", "이것은 강의 A",
            null);
        lectureRepository.save(lecture);
        return lectureRepository.getId();
    }

    private String setupMember() {
        final String nickname = "tester";
        final String email = "test@test.com";
        final String password = "1234ASDF";
        memberRepository.save(Member.of(nickname, email, password));
        return email;
    }

}
