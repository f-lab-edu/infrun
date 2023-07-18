package com.flab.infrun.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chapter;
    private String name;
    private Long lectureId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_file_id")
    private LectureFile lectureFile;

    private LectureDetail(String chapter, String name, Long lectureId, LectureFile lectureFile) {
        this.chapter = chapter;
        this.name = name;
        this.lectureId = lectureId;
        this.lectureFile = lectureFile;
    }

    public static LectureDetail of(String chapter, String name, Long lectureId,
        LectureFile lectureFile) {
        return new LectureDetail(chapter, name, lectureId, lectureFile);
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
        return Objects.equals(lectureFile, that.lectureFile);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (chapter != null ? chapter.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lectureId != null ? lectureId.hashCode() : 0);
        result = 31 * result + (lectureFile != null ? lectureFile.hashCode() : 0);
        return result;
    }
}
