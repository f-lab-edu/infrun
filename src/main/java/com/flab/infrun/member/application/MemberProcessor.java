package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.DuplicatedEmailException;
import org.springframework.stereotype.Component;

@Component
public class MemberProcessor {

    private final MemberRepository memberRepository;

    public MemberProcessor(final MemberRepository port) {
        this.memberRepository = port;
    }

    public Long register(SignupCommand command) {
        if (memberRepository.existsByEmail(command.email())) {
            throw new DuplicatedEmailException();
        }

        Member member = memberRepository.save(
            Member.of(
                command.nickname(),
                command.email(),
                command.password()));

        return member.getId();
    }
}
