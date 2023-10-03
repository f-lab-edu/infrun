package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.MemberVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberSignupProcessor {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberVerifier memberVerifier;

    @Transactional
    public void execute(final SignupCommand command) {
        verifyCommand(command);

        memberRepository.save(
            Member.of(command.nickname(), command.email(),
                passwordEncoder.encode(command.password())));
    }

    private void verifyCommand(final SignupCommand command) {
        memberVerifier.verifyNickname(command.nickname());
        memberVerifier.verifyEmail(command.email());
        memberVerifier.verifyPassword(command.password());
    }
}
