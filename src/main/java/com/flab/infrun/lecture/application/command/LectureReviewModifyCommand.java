package com.flab.infrun.lecture.application.command;

public record LectureReviewModifyCommand(
    Long lectureReviewId,
    String content,
    String memberEmail
) {

}
