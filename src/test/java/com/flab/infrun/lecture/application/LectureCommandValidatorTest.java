package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.lecture.application.command.LectureDetailCommand;
import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand.MultipartCommandInfo;
import com.flab.infrun.lecture.application.exception.DuplicateLecturePartNameException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureCommandValidatorTest {

    private LectureCommandValidator lectureCommandValidator;

    @BeforeEach
    void setup() {
        lectureCommandValidator = new LectureCommandValidator();
    }

    @Test
    @DisplayName("강의 파트-상세 중복 체크-presinged Request")
    void checkLecturePartNameDupPresigned() {
        PublishPreSignedUrlCommand publishPreSignedUrlCommand = setupUrlCommand();

        assertThatThrownBy(() -> lectureCommandValidator.validatePathNameUrl(
            publishPreSignedUrlCommand))

            .isInstanceOf(DuplicateLecturePartNameException.class);
    }

    @Test
    @DisplayName("강의 파트-상세 중복 체크-Lecture Register Request")
    void checkLecturePartNameDupRegister() {
        LectureRegisterCommand lectureRegisterCommand = setupRegisterCommand();

        assertThatThrownBy(() -> lectureCommandValidator.validatePathNameRegister(
            lectureRegisterCommand))

            .isInstanceOf(DuplicateLecturePartNameException.class);
    }

    private PublishPreSignedUrlCommand setupUrlCommand() {
        return new PublishPreSignedUrlCommand(List.of(
            new MultipartCommandInfo("기본/첫번째강의/스프링 강의1", 2),
            new MultipartCommandInfo("응용/두번째강의/스프링 강의2", 3),
            new MultipartCommandInfo("심화/세번째강의/스프링 강의3", 3),
            new MultipartCommandInfo("심화/세번째강의/스프링 강의3", 6)
        ), "memberId");
    }

    private LectureRegisterCommand setupRegisterCommand() {
        return new LectureRegisterCommand(
            "스프링 기본 강의",
            54000,
            2,
            "JAVA",
            "스프링 핵심 코어 강의 입니다.",
            List.of(
                new LectureDetailCommand("기본", "첫번째강의", "memberId/기본/첫번째강의/스프링 강의1", "uploadId",
                    null),
                new LectureDetailCommand("응용", "두번째강의", "memberId/응용/두번째강의/스프링 강의2", "uploadId",
                    null),
                new LectureDetailCommand("심화", "세번째강의", "memberId/심화/세번째강의/스프링 강의3", "uploadId",
                    null),
                new LectureDetailCommand("심화", "세번째강의", "memberId/심화/세번째강의/스프링 강의3", "uploadId",
                    null)
            ));
    }
}
