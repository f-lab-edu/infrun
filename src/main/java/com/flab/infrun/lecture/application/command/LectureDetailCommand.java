package com.flab.infrun.lecture.application.command;

import java.util.List;

public record LectureDetailCommand(
    String chapter,
    String name,
    String objectKey,
    String uploadId,
    List<String> etagList
) {

}
