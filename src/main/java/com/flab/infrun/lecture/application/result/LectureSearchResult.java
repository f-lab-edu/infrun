package com.flab.infrun.lecture.application.result;

import java.util.List;

public record LectureSearchResult(
    List<LectureInfoResult> lecturesList
) {

    public record LectureInfoResult(
        Long id,
        String name,
        int price,
        int lectureLevel,
        String skill,
        Long uploadUserId,
        String uploadUserName) {

    }
}
