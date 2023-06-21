package com.flab.infrun.lecture.application.command;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record LectureRegisterCommand(
    //todo-gradle 의존성 주입 Notnull 등
    String name,
    int price,
    String introduce,
    List<LectureDetailCommand> lectureDetailCommandList,
    List<MultipartFile> lectureFileList
) {

}
