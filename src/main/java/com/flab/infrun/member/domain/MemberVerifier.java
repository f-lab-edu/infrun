package com.flab.infrun.member.domain;

public interface MemberVerifier {

    void verifyNickname(final String nickname);

    void verifyEmail(final String email);

    void verifyPassword(final String password);
}
