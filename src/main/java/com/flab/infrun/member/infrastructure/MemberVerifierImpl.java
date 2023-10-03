package com.flab.infrun.member.infrastructure;

import static java.util.regex.Pattern.matches;

import com.flab.infrun.member.domain.MemberVerifier;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import com.flab.infrun.member.domain.exception.InvalidPasswordException;
import com.flab.infrun.member.infrastructure.persistence.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class MemberVerifierImpl implements MemberVerifier {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void verifyNickname(final String nickname) {
        if (!StringUtils.hasText(nickname) || memberJpaRepository.existsByNickname(nickname)) {
            throw new DuplicatedNicknameException();
        }
    }

    @Override
    public void verifyEmail(final String email) {
        if (!StringUtils.hasText(email) || memberJpaRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    @Override
    public void verifyPassword(final String password) {
        if (!StringUtils.hasText(password)
            || !matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[a-z])(?=.*[\\W_])[A-Za-z\\d\\W_]{8,16}$",
            password)
        ) {
            throw new InvalidPasswordException();
        }
    }
}
