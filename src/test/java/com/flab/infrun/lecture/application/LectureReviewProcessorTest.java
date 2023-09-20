package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.flab.infrun.lecture.application.command.LectureReviewDeleteCommand;
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
            lectureReviewRepository,
            null,
            null,
            new LectureCommandValidator()
        );
    }

    @Test
    @DisplayName("강의 리뷰 등록 실패 - NotFoundLecture")
    void registerLectureReviewLectureException() {
        Member member = setupMember();
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(15L,
            "review content");

        assertThatThrownBy(
            () -> processor.registerLectureReview(command, member)).isInstanceOf(
            NotFoundLectureException.class);
    }

    @Test
    @DisplayName("강의 리뷰 등록 성공")
    void registerLectureReview() {
        Member member = setupMember();
        LectureReviewRegisterCommand command = new LectureReviewRegisterCommand(
            setupLecture().getId(),
            "review content"
        );

        assertDoesNotThrow(() -> processor.registerLectureReview(command, member));
    }

    @Test
    @DisplayName("강의 리뷰 수정 실패 - NotFoundLectureReview")
    void modifyLectureReviewNotFoundException() {
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(1L, "content");

        assertThatThrownBy(
            () -> processor.modifyLectureReview(command, setupMember())).isInstanceOf(
            NotFoundLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 수정 실패 - InvalidAuthorizationLectureReview")
    void modifyLectureReviewAuthorizationException() {
        Member member = setupMember2();
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(
            setupLectureReview(setupLecture(), setupMember()).getId(), "content");

        assertThatThrownBy(
            () -> processor.modifyLectureReview(command, member)).isInstanceOf(
            InvalidAuthorizationLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 수정 성공")
    void modifyLectureReviewSuccess() {
        Member member = setupMember();
        LectureReview lectureReview = setupLectureReview(setupLecture(), member);
        String changeContent = "content_modified";
        LectureReviewModifyCommand command = new LectureReviewModifyCommand(lectureReview.getId(),
            changeContent);

        Long reviewId = processor.modifyLectureReview(command, member);
        LectureReview lectureReviewModified = lectureReviewRepository.findById(reviewId)
            .orElseThrow(NotFoundLectureReviewException::new);

        assertThat(lectureReviewModified.getContent()).isEqualTo(changeContent);
    }

    @Test
    @DisplayName("강의 리뷰 삭제 실패 - NotFoundLectureReview")
    void deleteLectureReviewNotFoundReviewException() {
        LectureReviewDeleteCommand command = new LectureReviewDeleteCommand(1L);
        assertThatThrownBy(
            () -> processor.deleteLectureReview(command, setupMember())).isInstanceOf(
            NotFoundLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 삭제 실패 - InvalidAuthorizationLectureReview")
    void deleteLectureReviewInvalidAuthorizationException() {
        LectureReview lectureReview = setupLectureReview(setupLecture(), setupMember());
        setupMember2();
        LectureReviewDeleteCommand command = new LectureReviewDeleteCommand(lectureReview.getId());

        assertThatThrownBy(
            () -> processor.deleteLectureReview(command, setupMember2())).isInstanceOf(
            InvalidAuthorizationLectureReviewException.class);
    }

    @Test
    @DisplayName("강의 리뷰 삭제 성공")
    void deleteLectureReviewSuccess() {
        Member member = setupMember();
        LectureReview lectureReview = setupLectureReview(setupLecture(), member);

        LectureReviewDeleteCommand command = new LectureReviewDeleteCommand(
            lectureReview.getId());
        Long deletedCount = processor.deleteLectureReview(command, member);

        assertThat(deletedCount).isEqualTo(1);
        assertThat(lectureReviewRepository.findById(lectureReview.getId())).isEmpty();
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

    private LectureReview setupLectureReview(Lecture lecture, Member member) {
        LectureReview lectureReview = LectureReview.of("reply002", lecture, member);
        return lectureReviewRepository.save(lectureReview);
    }
}
