package com.flab.infrun.lecture.domain;

import com.flab.infrun.lecture.domain.exception.InvalidAuthorizationLectureReviewException;
import com.flab.infrun.member.domain.Member;
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

    public String getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }

    private LectureReview(String content, Lecture lecture, Member member) {
        this.content = content;
        this.lecture = lecture;
        this.member = member;
    }

    public static LectureReview of(String content, Lecture lecture, Member member) {
        return new LectureReview(content, lecture, member);
    }

    public void checkReviewAuthorization(Member member) {
        if (!this.member.getId().equals(member.getId())) {
            throw new InvalidAuthorizationLectureReviewException();
        }
    }

    public void changeContent(String content) {
        this.content = content;
    }

    @VisibleForTesting
    void assignId(final Long id) {
        this.id = id;
    }
}
