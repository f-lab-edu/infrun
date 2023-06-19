package com.flab.infrun.lecture.application.command;

public record LectureDetailCommand(
    //todo-gradle 의존성 주입 Notnull 등
    String chapter,
    String name

) {

}
