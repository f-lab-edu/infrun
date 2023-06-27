package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.query.LectureSearchForNameQuery;
import com.flab.infrun.lecture.application.query.LectureSearchResult;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.presentation.response.LectureSearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LectureQueryProcessor {

    private final LectureRepository lectureRepository;

    public LectureSearchResponse searchLectureForName(LectureSearchForNameQuery query) {
        List<Lecture> lecture = lectureRepository.findByDetail(query.toSearch());
        List<LectureSearchResult> lectureSearchResult = lecture.stream()
            .map(lec -> new LectureSearchResult(lec.getId(), lec.getName(), lec.getPrice(),
                lec.getLectureLevel(), lec.getSkill(), lec.getIntroduce()))
            .toList();
        return new LectureSearchResponse(lectureSearchResult.stream().map(
            LectureSearchResult::toResponse).collect(
            Collectors.toList()));
    }
}
