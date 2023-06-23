package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberProcessor processor;

    public Long signup(final SignupCommand command) {
        return processor.register(command);
    }
}
