package com.flab.infrun.lecture.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LectureVideoFile {

    private Long id;
    //todo- Notnull Gradle 의존성 -> PR merge
    private final String url;
    private final String name;

    public LectureVideoFile(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public static LectureVideoFile of(String url, String name) {
        return new LectureVideoFile(url, name);
    }

    public void setId(long key) {
        this.id = key;
    }

}
