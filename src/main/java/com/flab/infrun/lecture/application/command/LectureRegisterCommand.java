package com.flab.infrun.lecture.application.command;

import com.flab.infrun.lecture.presentation.request.LectureDetailRequest;
import java.util.List;

public record LectureRegisterCommand(
    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce,
    List<LectureDetailRequest> lectureDetailRequest) {

}
