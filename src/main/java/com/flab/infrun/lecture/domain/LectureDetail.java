package com.flab.infrun.lecture.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class LectureDetail {

    private Long id;
    //todo- Notnull Gradle 의존성 -> PR merge
    private final String chapter;
    private final String name;
    private final Long lectureId;


    private LectureDetail(String chapter, String name, Long lectureId) {
        this.chapter = chapter;
        this.name = name;
        this.lectureId = lectureId;
    }

    public static LectureDetail of(String chapter, String name, Long lectureId) {
        return new LectureDetail(chapter, name, lectureId);
    }

    public void setId(long key) {
        this.id = key;
    }
}
