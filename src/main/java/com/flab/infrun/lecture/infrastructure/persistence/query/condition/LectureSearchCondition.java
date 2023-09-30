package com.flab.infrun.lecture.infrastructure.persistence.query.condition;

public record LectureSearchCondition(
    String name,
    Integer price,
    Integer lectureLevel,
    String skill,
    String uploadUserName
) {

}
