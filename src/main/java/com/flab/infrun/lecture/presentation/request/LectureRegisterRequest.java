package com.flab.infrun.lecture.presentation.request;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.domain.LectureLevel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record LectureRegisterRequest(
    @NotBlank @Size(max = 30)
    String name,
    @PositiveOrZero
    int price,
    LectureLevel lectureLevel,
    @NotBlank @Size(max = 30)
    String skill,
    @NotBlank @Size(max = 200)
    String introduce,
    @NotNull @Valid
    List<LectureDetailRequest> lectureDetailRequest
) {

    public LectureRegisterCommand toCommand(List<MultipartFile> multipartFile) {
        return new LectureRegisterCommand(name, price, lectureLevel, skill,
            introduce,
            lectureDetailRequest.stream().map(LectureDetailRequest::toCommand).toList(),
            multipartFile
        );
    }
}
