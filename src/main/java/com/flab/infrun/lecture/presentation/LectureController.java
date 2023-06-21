package com.flab.infrun.lecture.presentation;

import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping(value = "/lecture")
    //Todo-responseEntity body 로 변경
    public ResponseEntity<Object> registerLecture(
        @RequestPart("lecture") LectureRegisterRequest lecture,
        @RequestPart("file") List<MultipartFile> lectureVideoFile) {

        //todo-Role check (Teacher)

        //todo- file도 command로 변경해서 전송
        lectureVideoFile.forEach(lectureFacade::uploadFile);

        long lectureId = lectureFacade.registerLecture(lecture.toCommand());

        return null;
    }
}

