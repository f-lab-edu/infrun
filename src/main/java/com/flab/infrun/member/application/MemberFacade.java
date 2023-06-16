package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.SignupCommand;
import org.springframework.stereotype.Service;

@Service
public class MemberFacade {

    private final MemberProcessor processor;

    public MemberFacade(final MemberProcessor processor) {
        this.processor = processor;
    }

    public Long signup(SignupCommand command) {
        return processor.register(command);
    }
}
