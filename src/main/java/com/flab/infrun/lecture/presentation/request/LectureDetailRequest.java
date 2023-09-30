package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureDetailCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record LectureDetailRequest(

    @NotBlank @Size(max = 30)
    String chapter,
    @NotBlank @Size(max = 30)
    String name,
    @NotBlank
    String objectKey,
    @NotBlank
    String uploadId,
    List<String> etagList
) {

    public LectureDetailCommand toCommand() {
        return new LectureDetailCommand(chapter, name, objectKey, uploadId, etagList);
    }
}
