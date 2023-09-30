package com.flab.infrun.lecture.domain;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberFixture;

public class LectureFixture {

    private Long id = 1L;
    private String name = "객체지향의 사실과 오해";
    private int price = 30_000;
    private int lectureLevel = 1;
    private String skill = "java";
    private String introduce = "객체지향";
    private Member member = MemberFixture.aMemberFixture().build();

    public static LectureFixture aLectureFixture() {
        return new LectureFixture();
    }

    public LectureFixture id(final Long id) {
        this.id = id;
        return this;
    }

    public LectureFixture name(final String name) {
        this.name = name;
        return this;
    }

    public LectureFixture price(final int price) {
        this.price = price;
        return this;
    }

    public LectureFixture lectureLevel(final int lectureLevel) {
        this.lectureLevel = lectureLevel;
        return this;
    }

    public LectureFixture skill(final String skill) {
        this.skill = skill;
        return this;
    }

    public LectureFixture introduce(final String introduce) {
        this.introduce = introduce;
        return this;
    }

    public LectureFixture member(final Member member) {
        this.member = member;
        return this;
    }

    public Lecture build() {
        final Lecture lecture = Lecture.of(
            this.name,
            this.price,
            this.lectureLevel,
            this.skill,
            this.introduce,
            this.member);
        lecture.assignId(this.id);
        return lecture;
    }
}
