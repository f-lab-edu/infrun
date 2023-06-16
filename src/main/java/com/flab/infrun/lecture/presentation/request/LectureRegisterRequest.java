package com.flab.infrun.lecture.presentation.request;

public record LectureRegisterRequest(

    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce
) {

}
