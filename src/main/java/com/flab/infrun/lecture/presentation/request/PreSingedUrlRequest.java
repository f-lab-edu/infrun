package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PreSingedUrlRequest(
    @NotBlank
    String objectKey,
    @Positive
    Integer partCnt
) {

    public PublishPreSignedUrlCommand toCommand() {
        return new PublishPreSignedUrlCommand(objectKey, partCnt);
    }
}
