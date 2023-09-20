package com.flab.infrun.lecture.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand.MultipartCommandInfo;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
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
    @DisplayName("파일 명 중복 체크")
    void checkObjectPath() {
        PublishPreSignedUrlCommand publishPreSignedUrlCommand = setupCommand();

        assertThatThrownBy(() -> lectureCommandValidator.validateObjectPath(
            publishPreSignedUrlCommand))

            .isInstanceOf(DuplicateLectureFileNameException.class);
    }

    private PublishPreSignedUrlCommand setupCommand() {
        return new PublishPreSignedUrlCommand(List.of(
            new MultipartCommandInfo("1/스프링 강의1", 2),
            new MultipartCommandInfo("2/스프링 강의2", 3),
            new MultipartCommandInfo("3/스프링 강의3", 3),
            new MultipartCommandInfo("3/스프링 강의3", 6)
        ), "memberId");
    }

}