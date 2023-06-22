package com.flab.infrun.lecture.application.command;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record LectureRegisterCommand(
    String name,
    int price,
    String introduce,
    List<LectureDetailCommand> lectureDetailCommandList,
    List<MultipartFile> lectureFileList
) {

}
