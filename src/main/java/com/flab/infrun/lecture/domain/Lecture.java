package com.flab.infrun.lecture.domain;

import com.flab.infrun.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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
    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL)
    private List<LectureDetail> lectureDetailList;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Lecture(String name, int price, int level, String skill, String introduce,
        Member member) {
        this.name = name;
        this.price = price;
        this.lectureLevel = level;
        this.skill = skill;
        this.introduce = introduce;
        this.member = member;
    }

    public static Lecture of(String name, int price, int lectureLevel, String skill,
        String introduce, Member member) {
        return new Lecture(name, price, lectureLevel, skill, introduce, member);
    }

    public void addLectureDetail(List<LectureDetail> lectureDetailList) {
        this.lectureDetailList = lectureDetailList;
        lectureDetailList.forEach(detail -> detail.setLecture(this));
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

    public List<LectureDetail> getLectureDetailList() {
        return lectureDetailList;
    }

    @Override
    public String toString() {
        return "Lecture{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", lectureLevel=" + lectureLevel +
            ", skill='" + skill + '\'' +
            ", introduce='" + introduce + '\'' +
            '}';
    }
}
