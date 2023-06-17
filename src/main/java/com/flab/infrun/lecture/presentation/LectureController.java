package com.flab.infrun.lecture.presentation;

import com.flab.infrun.lecture.application.LectureFacade;
import com.flab.infrun.lecture.presentation.request.LectureRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/lecture", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.APPLICATION_JSON_VALUE})

    //Todo-responseEntity body 로 변경
    public ResponseEntity<Object> registerLecture(
        @RequestPart("lecture") LectureRegisterRequest lecture,
        @RequestPart("file") MultipartFile lectureVideoFile) {
        log.info("lecture :{}, fileName : {} ", lecture, lectureVideoFile.getOriginalFilename());
        //todo- file도 command로 변경해서 던질 것인지
        lectureFacade.uploadFile(lectureVideoFile);
        long lectureId = lectureFacade.registerLecture(lecture.toCommand());
        return null;
    }

    @PostMapping("/lectureV2")
    //Todo-responseEntity body 로 변경
    public ResponseEntity<Object> registerLecture(MultipartFile file) {

        log.info(file.getName());

        return null;
    }

}
