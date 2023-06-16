package com.flab.infrun.lecture.application.command;

public record LectureRegisterCommand(
    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce
) {

}
