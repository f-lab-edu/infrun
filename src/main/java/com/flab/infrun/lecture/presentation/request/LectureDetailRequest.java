package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureDetailCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LectureDetailRequest(

    @NotBlank @Size(max = 30)
    String chapter,
    @NotBlank @Size(max = 30)
    String name,
    String fileName

) {

    public LectureDetailCommand toCommand() {
        return new LectureDetailCommand(chapter, name, fileName);
    }
}
