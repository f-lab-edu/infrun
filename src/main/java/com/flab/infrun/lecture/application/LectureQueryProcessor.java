package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.query.LectureSearchQuery;
import com.flab.infrun.lecture.application.query.LectureSearchResult;
import com.flab.infrun.lecture.infrastructure.persistence.query.LectureQueryRepository;
import com.flab.infrun.lecture.presentation.response.LectureQueryResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureQueryProcessor {

    private final LectureQueryRepository lectureQueryRepository;

    public List<LectureQueryResponse> searchLecture(LectureSearchQuery lectureSearchQuery) {
        return lectureQueryRepository.search(
                lectureSearchQuery.toCondition()).stream()
            .map(dto -> new LectureSearchResult(dto.id(), dto.name(), dto.price(),
                dto.lectureLevel(), dto.skill(), dto.uploadUserId(),
                dto.uploadUserName()).toResponse())
            .toList();
    }
}
