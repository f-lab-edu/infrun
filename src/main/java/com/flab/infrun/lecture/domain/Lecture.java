package com.flab.infrun.lecture.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class Lecture {

    private Long id;
    //todo- Notnull Gradle 의존성 -> PR merge
    private final String name;
    private final int price;
    private final String introduce;


    private Lecture(String name, int price, String introduce) {
        this.name = name;
        this.price = price;
        this.introduce = introduce;
    }

    public static Lecture of(String name, int price, String introduce) {
        return new Lecture(name, price, introduce);
    }

    public void setId(long key) {
        this.id = key;
    }
}
