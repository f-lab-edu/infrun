package com.flab.infrun.lecture.presentation.response;

import com.flab.infrun.lecture.application.result.LectureSearchResult;
import java.util.List;
import java.util.stream.Collectors;

public record LectureQueryResponse(
    List<LectureInfo> lectureList
) {

    public static LectureQueryResponse from(LectureSearchResult lectureSearchResult) {
        return new LectureQueryResponse(lectureSearchResult.lecturesList().stream()
            .map(lectureInfoResult -> new LectureInfo(
                lectureInfoResult.id(),
                lectureInfoResult.name(),
                lectureInfoResult.price(),
                lectureInfoResult.lectureLevel(),
                lectureInfoResult.skill(),
                lectureInfoResult.uploadUserId(),
                lectureInfoResult.uploadUserName()
            ))
            .collect(Collectors.toList()));
    }

    record LectureInfo(
        Long id,
        String name,
        int price,
        int lectureLevel,
        String skill,
        Long uploadUserId,
        String uploadUserName) {

    }
}
