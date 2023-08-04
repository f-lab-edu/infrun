package com.flab.infrun.lecture.application.command;

public record LectureReviewDeleteCommand(
    Long lectureReviewId,
    String memberEmail
) {

}
