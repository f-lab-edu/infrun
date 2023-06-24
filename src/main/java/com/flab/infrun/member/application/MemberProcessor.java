package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.LoginCommand;
import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import com.flab.infrun.member.infrastructure.session.SessionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberProcessor {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionStorage sessionStorage;

    @Transactional
    public Long register(final SignupCommand command) {
        validateRegisterMember(command);

        final Member member = memberRepository.save(
            Member.of(command.nickname(), command.email(),
                passwordEncoder.encode(command.password())));

        return member.getId();
    }

    @Transactional(readOnly = true)
    public String login(final LoginCommand command) {
        final Member member = memberRepository.findByEmailAndPassword(command.email(),
                command.password())
            .orElseThrow(NotFoundMemberException::new);

        return sessionStorage.store(member.getEmail(), member.getPassword())
            .getToken();
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
