package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberProcessor {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long register(final SignupCommand command) {
        validateRegisterMember(command);

        final Member member = memberRepository.save(
            Member.of(command.nickname(), command.email(),
                passwordEncoder.encode(command.password())));

        return member.getId();
    }

    private void validateRegisterMember(final SignupCommand command) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new DuplicatedEmailException();
        }
        if (memberRepository.existsByNickname(command.nickname())) {
            throw new DuplicatedNicknameException();
        }
    }
}
