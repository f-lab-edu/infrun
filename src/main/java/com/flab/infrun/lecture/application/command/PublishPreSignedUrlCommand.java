package com.flab.infrun.lecture.application.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record PublishPreSignedUrlCommand(
    List<MultipartCommandInfo> multipartInfos,
    String memberId
) {

    public record MultipartCommandInfo(
        @NotBlank
        String objectPath,
        @Positive
        Integer partCnt
    ) {

    }
}
