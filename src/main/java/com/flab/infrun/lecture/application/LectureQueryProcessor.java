package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import com.flab.infrun.lecture.infrastructure.data.LectureDTO;
import com.flab.infrun.lecture.infrastructure.persistence.query.LectureQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureQueryProcessor {

    private final LectureQueryRepository lectureQueryRepository;

    public List<LectureDTO> searchLecture(LectureSearchQuery lectureSearchQuery) {
        return lectureQueryRepository.search(lectureSearchQuery.toCondition());
    }
}
