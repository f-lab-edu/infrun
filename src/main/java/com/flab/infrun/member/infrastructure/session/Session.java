package com.flab.infrun.member.infrastructure.session;

import java.util.Objects;

public class Session {

    private final String token;

    private Session(final String token) {
        this.token = token;
    }

    public static Session from(final String token) {
        return new Session(token);
    }

    public boolean isSameToken(final String token) {
        return Objects.equals(this.token, token);
    }
}
