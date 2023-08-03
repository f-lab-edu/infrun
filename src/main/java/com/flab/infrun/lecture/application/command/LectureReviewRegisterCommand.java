package com.flab.infrun.lecture.application.command;

public record LectureReviewRegisterCommand(
    Long lectureId,
    String content,
    String memberEmail
) {

}
