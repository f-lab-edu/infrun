package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.query.LectureSearchForNameQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LectureSearchForTeacherRequest(
    @NotBlank @Size(max = 30)
    String teacherName,
    LectureSearchDetailRequest lectureSearchDetailRequest

) {

    public LectureSearchForNameQuery toCommand() {
        return new LectureSearchForNameQuery(teacherName, lectureSearchDetailRequest.toCommand());
    }
}
