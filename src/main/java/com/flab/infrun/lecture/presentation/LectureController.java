package com.flab.infrun.lecture.presentation;

import com.flab.infrun.common.response.Response;
import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import com.flab.infrun.lecture.presentation.request.LectureSearchRequest;
import com.flab.infrun.lecture.presentation.response.LectureQueryResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping
    public ResponseEntity<Response<Long>> registerLecture(
        @Valid @RequestPart("lecture") LectureRegisterRequest lecture,
        @RequestPart("file") List<MultipartFile> lectureVideoFile) {
        //todo Role check (Teacher)

        var result = lectureFacade.registerLecture(lecture.toCommand(lectureVideoFile));

        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success(result));
    }

    @GetMapping
    public ResponseEntity<Response<List<LectureQueryResponse>>> searchLecture(
        @Valid LectureSearchRequest lectureSearch
    ) {
        var result = lectureFacade.searchLecture(lectureSearch.toQuery());
        return ResponseEntity.status(HttpStatus.OK).body(Response.success(result));
    }
}

