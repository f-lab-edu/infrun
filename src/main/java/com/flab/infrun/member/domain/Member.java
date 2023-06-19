package com.flab.infrun.member.domain;

import com.google.common.annotations.VisibleForTesting;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

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

    @VisibleForTesting
    void assignId(Long id) {
        this.id = id;
    }
}
