package com.flab.infrun.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_file_id")
    private LectureFile lectureFile;

    private LectureDetail(String chapter, String name, LectureFile lectureFile) {
        this.chapter = chapter;
        this.name = name;
        this.lectureFile = lectureFile;
    }

    public static LectureDetail of(String chapter, String name,
        LectureFile lectureFile) {
        return new LectureDetail(chapter, name, lectureFile);
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

    public LectureFile getLectureFile() {
        return lectureFile;
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
}
