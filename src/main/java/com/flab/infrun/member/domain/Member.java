package com.flab.infrun.member.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public final class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nickname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private Member(final String nickname, final String email, final String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public static Member of(
        final String nickname,
        final String email,
        final String password) {
        return new Member(nickname, email, password);
    }

    public void promoteToTeacher() {
        this.role = Role.TEACHER;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @VisibleForTesting
    public void assignId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
