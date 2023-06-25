package com.flab.infrun.lecture.presentation;

import com.flab.infrun.common.response.Response;
import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping(value = "/lecture")
    public ResponseEntity<Response<Long>> registerLecture(
        @Valid @RequestPart("lecture") LectureRegisterRequest lecture,
        @RequestPart("file") List<MultipartFile> lectureVideoFile) {
        log.debug("lecutre : {}", lecture);
        lectureVideoFile.forEach(file -> log.debug("fileName :{}", file.getOriginalFilename()));
        //todo-Role check (Teacher)

        var result = lectureFacade.registerLecture(lecture.toCommand(lectureVideoFile));

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(result));
    }

    @GetMapping(value = "/lecture")
    public ResponseEntity<Response<Long>> searchLecture(
        @Valid @RequestBody LectureRegisterRequest lecture) {
        log.debug("lecutre : {}", lecture);

        var result = lectureFacade.registerLecture(lecture.toCommand(null));

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(result));
    }
}

