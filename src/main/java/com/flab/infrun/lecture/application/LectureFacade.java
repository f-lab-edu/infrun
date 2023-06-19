package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class LectureFacade {

    private final LectureProcessor lectureProcessor;

    public long registerLecture(LectureRegisterCommand command) {
        return lectureProcessor.registerLecture(command);
    }

    public void uploadFile(MultipartFile lectureVideoFile) {
        lectureProcessor.uploadFile(lectureVideoFile);
    }


}
