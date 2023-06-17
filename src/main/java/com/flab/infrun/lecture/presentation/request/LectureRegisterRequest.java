package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;

public record LectureRegisterRequest(

    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce
) {

    public LectureRegisterCommand toCommand() {
        return new LectureRegisterCommand(name, price, introduce);
    }

}
