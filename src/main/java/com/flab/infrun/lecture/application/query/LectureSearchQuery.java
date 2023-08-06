package com.flab.infrun.lecture.application.query;

import com.flab.infrun.lecture.infrastructure.persistence.query.condition.LectureSearchCondition;

public record LectureSearchQuery(
    String name,
    Integer price,
    Integer lectureLevel,
    String skill,
    String uploadUserName

) {

    public LectureSearchCondition toCondition() {
        return new LectureSearchCondition(name, price, lectureLevel, skill, uploadUserName);
    }
}
