package com.flab.infrun.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int lectureLevel;
    private String skill;
    private String introduce;
    private long userId;

    private Lecture(String name, int price, int level, String skill, String introduce,
        long userId) {
        this.name = name;
        this.price = price;
        this.lectureLevel = level;
        this.skill = skill;
        this.introduce = introduce;
        this.userId = userId;
    }

    public static Lecture of(String name, int price, int lectureLevel, String skill,
        String introduce, long userId) {
        return new Lecture(name, price, lectureLevel, skill, introduce, userId);
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

    public int getPrice() {
        return price;
    }

    public int getLectureLevel() {
        return lectureLevel;
    }

    public String getSkill() {
        return skill;
    }

    public String getIntroduce() {
        return introduce;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lecture lecture = (Lecture) o;

        if (price != lecture.price) {
            return false;
        }
        if (lectureLevel != lecture.lectureLevel) {
            return false;
        }
        if (userId != lecture.userId) {
            return false;
        }
        if (!Objects.equals(id, lecture.id)) {
            return false;
        }
        if (!Objects.equals(name, lecture.name)) {
            return false;
        }
        if (!Objects.equals(skill, lecture.skill)) {
            return false;
        }
        return Objects.equals(introduce, lecture.introduce);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + lectureLevel;
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        result = 31 * result;
        return result;
    }
}
