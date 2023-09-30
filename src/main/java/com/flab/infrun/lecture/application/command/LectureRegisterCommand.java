package com.flab.infrun.lecture.application.command;

import java.util.List;

public record LectureRegisterCommand(
    String name,
    int price,
    int level,
    String skill,
    String introduce,
    List<LectureDetailCommand> lectureDetailCommandList
) {

}
