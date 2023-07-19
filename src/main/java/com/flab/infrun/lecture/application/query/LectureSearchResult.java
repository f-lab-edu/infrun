package com.flab.infrun.lecture.application.query;

import com.flab.infrun.lecture.presentation.response.LectureQueryResponse;

public record LectureSearchResult(
    Long id,
    String name,
    int price,
    int lectureLevel,
    String skill,
    Long uploadUserId,
    String uploadUserName
) {

    public LectureQueryResponse toResponse() {
        return new LectureQueryResponse(id, name, price, lectureLevel, skill, uploadUserId,
            uploadUserName);
    }
}
