package com.flab.infrun.lecture.infrastructure.data;

public record LectureDTO(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    Long uploadUserId,
    String uploadUserName) {

}
