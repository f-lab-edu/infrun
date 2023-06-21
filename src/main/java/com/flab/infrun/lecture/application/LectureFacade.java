package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureFacade {

    private final LectureProcessor lectureProcessor;

    public long registerLecture(LectureRegisterCommand command) {
        return lectureProcessor.registerLecture(command);
    }

}
