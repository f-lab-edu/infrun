package com.flab.infrun.lecture.domain;

import java.util.Objects;

public class LectureDetail {

    private Long id;
    //todo- validating POJO
    private final String chapter;
    private final String name;
    private final Long lectureId;
    private final Long fileId;

    private LectureDetail(Long id, String chapter, String name, Long lectureId, Long fileId) {
        this.chapter = chapter;
        this.name = name;
        this.lectureId = lectureId;
        this.fileId = fileId;
    }

    public static LectureDetail of(String chapter, String name, Long lectureId, Long fileId) {
        return new LectureDetail(null, chapter, name, lectureId, fileId);
    }

    public void setId(long key) {
        this.id = key;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LectureDetail that = (LectureDetail) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(chapter, that.chapter)) {
            return false;
        }
        if (!Objects.equals(name, that.name)) {
            return false;
        }
        if (!Objects.equals(lectureId, that.lectureId)) {
            return false;
        }
        return Objects.equals(fileId, that.fileId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (chapter != null ? chapter.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lectureId != null ? lectureId.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        return result;
    }
}
