package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.flab.infrun.lecture.application.command.LectureReviewModifyCommand;
import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureReview;
import com.flab.infrun.lecture.domain.StubLectureRepository;
import com.flab.infrun.lecture.domain.StubLectureReviewRepository;
import com.flab.infrun.lecture.domain.exception.InvalidAuthorizationLectureReviewException;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureException;
import com.flab.infrun.lecture.domain.exception.NotFoundLectureReviewException;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.StubMemberRepository;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class LectureReviewProcessorTest {

    private LectureCommandProcessor processor;
    private final StubLectureRepository lectureRepository = new StubLectureRepository();
    private final StubLectureReviewRepository lectureReviewRepository = new StubLectureReviewRepository();
    private final StubMemberRepository memberRepository = new StubMemberRepository();

    @BeforeEach
    void setup() {
        processor = new LectureCommandProcessor(
            //todo- utils stub refactor
            lectureRepository,
            null,
            lectureReviewRepository,
            memberRepository,
            null
        );
    }

    @Test
    @DisplayName("강의 리뷰 등록 실패 - NotFoundLecture")
    void registerLectureReviewLectureException() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(15L,
            "review content", setupMember().getEmail());

        assertThatThrownBy(() -> processor.registerLectureReview(command)).isInstanceOf(
            NotFoundLectureException.class);
    }

    @Test
    @DisplayName("강의 리뷰 등록 실패 - NotFoundMember")
    void registerLectureReviewMemberException() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(
            setupLecture().getId(),
            "review content",
            "test@test.com");

        assertThatThrownBy(() -> processor.registerLectureReview(command)).isInstanceOf(
            NotFoundMemberException.class);
    }

    @Test
    @DisplayName("강의 리뷰 등록 성공")
    void registerLectureReview() {
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(
            setupLecture().getId(),
            "review content",
            setupMember().getEmail());

        assertDoesNotThrow(() -> processor.registerLectureReview(command));
    }

    @Test
    @DisplayName("강의 리뷰 수정 실패 - NotFoundLectureReview")
    void modifyLectureReviewNotFoundException() {
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(1L, "content",
            "test@test.com");

        assertThatThrownBy(() -> processor.modifyLectureReview(command)).isInstanceOf(
            NotFoundLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 수정 실패 - NotFoundMember")
    void modifyLectureReviewMemberException() {
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(
            setupLectureReview().getId(),
            "content", "nono@test.com");

        assertThatThrownBy(() -> processor.modifyLectureReview(command)).isInstanceOf(
            NotFoundMemberException.class);
    }

    @Test
    @DisplayName("강의 리뷰 수정 실패 - InvalidAuthorizationLectureReview")
    void modifyLectureReviewAuthorizationException() {
        setupMember2();
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(
            setupLectureReview().getId(), "content", "test2@test.com");

        assertThatThrownBy(() -> processor.modifyLectureReview(command)).isInstanceOf(
            InvalidAuthorizationLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 수정 성공")
    void modifyLectureReviewSuccess() {
        LectureReview lectureReview = setupLectureReview();
        String changeContent = "content_modified";
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(
            lectureReview.getId(), changeContent, setupMember().getEmail());

        Long reviewId = processor.modifyLectureReview(command);
        LectureReview lectureReviewModified = lectureReviewRepository.findById(reviewId)
            .orElseThrow(NotFoundLectureReviewException::new);

        assertThat(lectureReviewModified.getContent()).isEqualTo(changeContent);
    }

    private Lecture setupLecture() {
        return lectureRepository.save(Lecture.of("Lecture1", 20000, 1, "JAVA", "이것은 강의 A",
            null));
    }

    private Member setupMember() {
        final String nickname = "tester";
        final String email = "test@test.com";
        final String password = "1234ASDF";
        return memberRepository.save(Member.of(nickname, email, password));
    }

    private Member setupMember2() {
        final String nickname = "tester2";
        final String email = "test2@test.com";
        final String password = "1234ASDF";
        return memberRepository.save(Member.of(nickname, email, password));
    }

    private LectureReview setupLectureReview() {
        LectureReview lectureReview = LectureReview.of("reply002", setupLecture(), setupMember());
        return lectureReviewRepository.save(lectureReview);
    }
}
