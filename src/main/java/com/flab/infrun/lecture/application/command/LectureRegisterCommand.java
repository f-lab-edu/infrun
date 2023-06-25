package com.flab.infrun.lecture.application.command;

import com.flab.infrun.lecture.domain.LectureLevel;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record LectureRegisterCommand(
    String name,
    int price,
    LectureLevel level,
    String skill,
    String introduce,
    List<LectureDetailCommand> lectureDetailCommandList,
    List<MultipartFile> lectureFileList
) {

}
