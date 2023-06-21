package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import java.util.List;

public record LectureRegisterRequest(
    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce,
    List<LectureDetailRequest> lectureDetailRequest
) {

    public LectureRegisterCommand toCommand() {
        return new LectureRegisterCommand(name, price, introduce, lectureDetailRequest);
    }
}
