package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.query.LectureSearchForNameQuery;
import com.flab.infrun.lecture.presentation.response.LectureSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureFacade {

    private final LectureProcessor lectureProcessor;
    private final LectureQueryProcessor lectureQueryProcessor;

    public long registerLecture(LectureRegisterCommand command) {
        return lectureProcessor.registerLecture(command);
    }

    public LectureSearchResponse searchLectureForName(LectureSearchForNameQuery command) {
        return lectureQueryProcessor.searchLectureForName(command);
    }
}
