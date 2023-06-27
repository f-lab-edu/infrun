package com.flab.infrun.lecture.application.query;

import com.flab.infrun.lecture.presentation.response.LectureSearchResponse;

public record LectureSearchResult(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    String introduce
) {

    public LectureSearchResponse toResponse() {
        return new LectureSearchResponse(id, name, price, lectureLevel, skill, introduce);
    }
}
