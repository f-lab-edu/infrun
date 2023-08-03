package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LectureReviewRegisterRequest(
    @NotBlank
    Long lectureId,

    @NotBlank
    @Size(max = 400)
    String content,
    @NotBlank
    @Email
    String memberEmail

) {

    public LectureReviewRegisterCommand toCommand() {
        return new LectureReviewRegisterCommand(lectureId, content, memberEmail);
    }
}
