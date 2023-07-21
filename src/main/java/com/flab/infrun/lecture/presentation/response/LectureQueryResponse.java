package com.flab.infrun.lecture.presentation.response;

public record LectureQueryResponse(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    Long uploadUserId,
    String uploadUserName
) {

}
