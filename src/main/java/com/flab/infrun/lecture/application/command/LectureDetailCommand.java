package com.flab.infrun.lecture.application.command;

public record LectureDetailCommand(
    String chapter,
    String name,
    String fileName

) {

}
