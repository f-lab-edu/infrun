package com.flab.infrun.lecture.presentation.response;

public record LectureSearchListResponse(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    String introduce
) {

}
