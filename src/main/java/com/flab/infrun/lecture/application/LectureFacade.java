package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.command.LectureReviewRegisterCommand;
import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import com.flab.infrun.lecture.presentation.response.LectureQueryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LectureFacade {

    private final LectureCommandProcessor lectureCommandProcessor;
    private final LectureQueryProcessor lectureQueryProcessor;

    public long registerLecture(LectureRegisterCommand command) {
        return lectureCommandProcessor.registerLecture(command);
    }

    public List<LectureQueryResponse> searchLecture(LectureSearchQuery query) {
        return lectureQueryProcessor.searchLecture(query);
    }

    public Long registerLectureReview(LectureReviewRegisterCommand command) {
        return lectureCommandProcessor.registerLectureReview(command);
    }
}
