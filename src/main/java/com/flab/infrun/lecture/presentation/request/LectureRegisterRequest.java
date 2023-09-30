package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;

public record LectureRegisterRequest(
    @NotBlank @Size(max = 30)
    String name,
    @PositiveOrZero
    int price,
    @Positive
    int lectureLevel,
    @NotBlank @Size(max = 30)
    String skill,
    @NotBlank @Size(max = 200)
    String introduce,
    @NotNull @Valid
    List<LectureDetailRequest> lectureDetailRequest
) {

    public LectureRegisterCommand toCommand() {
        return new LectureRegisterCommand(name, price, lectureLevel, skill, introduce,
            lectureDetailRequest.stream().map(LectureDetailRequest::toCommand).toList());
    }
}
