package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import com.flab.infrun.member.domain.exception.DuplicatedNicknameException;
import org.springframework.stereotype.Component;

@Component
public class MemberProcessor {

    private final MemberRepository memberRepository;

    public MemberProcessor(final MemberRepository port) {
        this.memberRepository = port;
    }

    public Long register(SignupCommand command) {
        registerValidate(command);

        Member member = memberRepository.save(
            Member.of(
                command.nickname(),
                command.email(),
                command.password()));

        return member.getId();
    }

    private void registerValidate(final SignupCommand command) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new DuplicatedEmailException();
        }
        if (memberRepository.existsByNickname(command.nickname())) {
            throw new DuplicatedNicknameException();
        }
    }
}
