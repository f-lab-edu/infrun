package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand;
import com.flab.infrun.lecture.application.command.PublishPreSignedUrlCommand.MultipartCommandInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record PreSingedUrlRequest(
    List<MultipartInfo> multipartInfos
) {

    public PublishPreSignedUrlCommand toCommand(Long memberId) {
        return new PublishPreSignedUrlCommand(
            multipartInfos.stream().map(MultipartInfo::toCommand).toList(),
            String.valueOf(memberId));
    }

    record MultipartInfo(
        @NotBlank
        String objectPath,
        @Positive
        Integer partCnt
    ) {

        MultipartCommandInfo toCommand() {
            return new MultipartCommandInfo(objectPath, partCnt);
        }
    }
}
