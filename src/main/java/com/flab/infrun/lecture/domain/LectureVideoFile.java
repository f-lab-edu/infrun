package com.flab.infrun.lecture.domain;

import java.util.Objects;

public class LectureVideoFile {

    private Long id;
    //todo- Notnull Gradle 의존성 -> PR merge
    private final String url;
    private final String name;

    private LectureVideoFile(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public static LectureVideoFile of(String url, String name) {
        return new LectureVideoFile(url, name);
    }

    public void setId(long key) {
        this.id = key;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LectureVideoFile that = (LectureVideoFile) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(url, that.url)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
