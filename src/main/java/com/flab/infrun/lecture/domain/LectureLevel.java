package com.flab.infrun.lecture.domain;

public enum LectureLevel {
    EASY("1"),
    NORMAL("2"),
    HARD("3");

    private final String value;

    LectureLevel(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
