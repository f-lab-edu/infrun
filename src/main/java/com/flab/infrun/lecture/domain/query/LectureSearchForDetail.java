package com.flab.infrun.lecture.domain.query;

public record LectureSearchForDetail(
    String lectureName,
    String teacherName,
    Integer price,
    Integer lectureLevel,
    String skill
) {

}
