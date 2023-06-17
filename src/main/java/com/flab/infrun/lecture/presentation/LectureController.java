package com.flab.infrun.lecture.presentation;

import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping("/lecture")
    //Todo-responseEntity body 로 변경
    public ResponseEntity<Object> registerLecture(@RequestBody LectureRegisterRequest request) {
        lectureFacade.registerLecture(request.toCommand());
        return null;
    }

}
