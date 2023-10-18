package com.flab.infrun.member.domain;

public enum Role {

    USER("ROLE_USER"),
    TEACHER("ROLE_TEACHER");

    private final String value;

    Role(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
