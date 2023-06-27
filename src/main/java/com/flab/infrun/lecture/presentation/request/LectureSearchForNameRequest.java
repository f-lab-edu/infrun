package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.query.LectureSearchForNameQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LectureSearchForNameRequest(
    @NotBlank @Size(max = 30)
    String lectureName,
    LectureSearchDetailRequest lectureSearchDetailRequest

) {

    public LectureSearchForNameQuery toCommand() {
        return new LectureSearchForNameQuery(lectureName, lectureSearchDetailRequest.toCommand());
    }
}
