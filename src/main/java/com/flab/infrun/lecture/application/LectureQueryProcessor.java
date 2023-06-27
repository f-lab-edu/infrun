package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.query.LectureSearchForNameQuery;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.presentation.response.LectureSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureQueryProcessor {

    private final LectureRepository lectureRepository;

    public LectureSearchResponse searchLectureForName(LectureSearchForNameQuery query) {
        lectureRepository.findByDetail(query.toSearch());
        return null;
    }
}
