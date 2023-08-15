package com.flab.infrun.member.domain;

public class MemberFixture {

    private Long id = 1L;
    private String nickname = "test";
    private String email = "test@test.com";
    private String password = "test1234";
    private Role role = Role.USER;

    public static MemberFixture aMemberFixture() {
        return new MemberFixture();
    }

    public MemberFixture id(final Long id) {
        this.id = id;
        return this;
    }

    public MemberFixture nickname(final String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberFixture email(final String email) {
        this.email = email;
        return this;
    }

    public MemberFixture password(final String password) {
        this.password = password;
        return this;
    }

    public MemberFixture role(final Role role) {
        this.role = role;
        return this;
    }

    public Member build() {
        final Member member = Member.of(this.nickname, this.email, this.password);
        member.assignId(this.id);

        if (this.role == Role.TEACHER) {
            member.promoteToTeacher();
        }

        return member;
    }
}
