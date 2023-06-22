package com.flab.infrun.lecture.domain;

import java.util.Objects;

public class Lecture {

    //todo- validating?
    private Long id;
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

        Lecture lecture = (Lecture) o;

        if (price != lecture.price) {
            return false;
        }
        if (!Objects.equals(id, lecture.id)) {
            return false;
        }
        if (!Objects.equals(name, lecture.name)) {
            return false;
        }
        return Objects.equals(introduce, lecture.introduce);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        return result;
    }
}
