package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.query.LectureSearchDetailQuery;

public record LectureSearchDetailRequest(

    boolean isFree,
    int lectureLevel,
    String skill
) {

    public LectureSearchDetailQuery toCommand() {
        return new LectureSearchDetailQuery(isFree, lectureLevel, skill);
    }
}
