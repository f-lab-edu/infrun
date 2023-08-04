package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureReviewModifyCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LectureReviewModifyRequest(
    @NotNull
    Long lectureReviewId,
    @NotBlank
    @Size(max = 400)
    String content,
    @NotBlank
    @Email
    String memberEmail

) {

    public LectureReviewModifyCommand toCommand() {
        return new LectureReviewModifyCommand(lectureReviewId, content, memberEmail);
    }
}
