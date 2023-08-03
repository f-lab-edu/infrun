package com.flab.infrun.lecture.domain;

import com.flab.infrun.member.domain.Member;
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
public class LectureReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Long getId() {
        return id;
    }

    public Lecture getLecture() {
        return lecture;
    }

    private LectureReview(String content, Lecture lecture, Member member) {
        this.content = content;
        this.lecture = lecture;
        this.member = member;
    }

    public static LectureReview of(String content, Lecture lecture, Member member) {
        return new LectureReview(content, lecture, member);
    }

}
