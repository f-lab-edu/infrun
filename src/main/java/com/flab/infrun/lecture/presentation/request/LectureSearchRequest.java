package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record LectureSearchRequest(
    @Size(max = 30)
    String name,
    Integer price,
    @Positive
    Integer lectureLevel,
    @Size(max = 30)
    String skill,
    @Size(max = 30)
    String uploadUserName

) {

    public LectureSearchQuery toQuery() {
        return new LectureSearchQuery(name, price, lectureLevel, skill, uploadUserName);
    }
}
