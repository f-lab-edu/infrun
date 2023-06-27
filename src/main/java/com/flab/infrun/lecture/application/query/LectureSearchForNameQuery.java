package com.flab.infrun.lecture.application.query;

import com.flab.infrun.lecture.domain.query.LectureSearchForDetail;

public record LectureSearchForNameQuery(
    String lectureName,
    LectureSearchDetailQuery lectureSearchDetailCommand
) {

    public LectureSearchForDetail toSearch() {
        return new LectureSearchForDetail(lectureName, null,
            lectureSearchDetailCommand.isFree() ? 0 : null,
            lectureSearchDetailCommand.lectureLevel(),
            lectureSearchDetailCommand.skill());
    }
}
