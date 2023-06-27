package com.flab.infrun.lecture.application.query;

public record LectureSearchDetailQuery(
    boolean isFree,
    int lectureLevel,
    String skill
) {

}
