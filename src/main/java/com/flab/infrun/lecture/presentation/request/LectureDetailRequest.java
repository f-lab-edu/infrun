package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureDetailCommand;

public record LectureDetailRequest(
    //todo-gradle 의존성 주입 Notnull 등
    String chapter,
    String name

) {

    public LectureDetailCommand toCommand() {
        return new LectureDetailCommand(chapter, name);
    }

}
