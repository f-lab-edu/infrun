package com.flab.infrun.lecture.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String name;

    private LectureFile(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public static LectureFile of(String url, String name) {
        return new LectureFile(url, name);
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
    public String toString() {
        return "LectureVideoFile{" +
            "id=" + id +
            ", url='" + url + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
