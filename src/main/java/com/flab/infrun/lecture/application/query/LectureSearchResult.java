package com.flab.infrun.lecture.application.query;

import com.flab.infrun.lecture.presentation.response.LectureSearchListResponse;

public record LectureSearchResult(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    String introduce
) {

    public LectureSearchListResponse toResponse() {
        return new LectureSearchListResponse(id, name, price, lectureLevel, skill, introduce);
    }
}
