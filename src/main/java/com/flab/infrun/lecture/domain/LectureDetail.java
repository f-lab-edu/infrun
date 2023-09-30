package com.flab.infrun.lecture.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    private String objectKey;

    private LectureDetail(String chapter, String name, String objectKey) {
        this.chapter = chapter;
        this.name = name;
        this.objectKey = objectKey;
    }

    public static LectureDetail of(String chapter, String name, String objectKey) {
        return new LectureDetail(chapter, name, objectKey);
    }

    void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Long getId() {
        return id;
    }

    public Lecture getLecture() {
        return lecture;
    }

    @Override
    public String toString() {
        return "LectureDetail{" +
            "id=" + id +
            ", chapter='" + chapter + '\'' +
            ", name='" + name + '\'' +
            ", lecture=" + lecture +
            '}';
    }

    @VisibleForTesting
    void assignId(final Long id) {
        this.id = id;
    }
}
