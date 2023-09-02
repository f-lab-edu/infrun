package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureReviewDeleteCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LectureReviewDeleteRequest(
    @NotNull
    Long lectureReviewId,
    @NotBlank
    @Email
    String memberEmail

) {

    public LectureReviewDeleteCommand toCommand() {
        return new LectureReviewDeleteCommand(lectureReviewId, memberEmail);
    }
}
