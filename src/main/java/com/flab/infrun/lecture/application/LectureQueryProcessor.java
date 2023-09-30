package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import com.flab.infrun.lecture.application.result.LectureSearchResult;
import com.flab.infrun.lecture.application.result.LectureSearchResult.LectureInfoResult;
import com.flab.infrun.lecture.infrastructure.persistence.query.LectureQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureQueryProcessor {

    private final LectureQueryRepository lectureQueryRepository;

    public LectureSearchResult searchLecture(LectureSearchQuery lectureSearchQuery) {
        return new LectureSearchResult(lectureQueryRepository.search(
                lectureSearchQuery.toCondition()).stream()
            .map(dto -> new LectureInfoResult(dto.id(), dto.name(), dto.price(),
                dto.lectureLevel(), dto.skill(), dto.uploadUserId(),
                dto.uploadUserName()))
            .toList());
    }
}
