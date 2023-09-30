package com.flab.infrun.lecture.application.command;

public record PublishPreSignedUrlCommand(
    String objectKey,
    int partCnt
) {

}
