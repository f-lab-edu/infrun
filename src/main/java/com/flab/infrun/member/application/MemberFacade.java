package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.LoginCommand;
import com.flab.infrun.member.application.command.SignupCommand;
import com.flab.infrun.member.application.result.LoginResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFacade {

    private final MemberSignupProcessor signupProcessor;
    private final MemberLoginProcessor loginProcessor;

    public void signup(final SignupCommand command) {
        signupProcessor.execute(command);
    }

    public LoginResult login(final LoginCommand command) {
        return loginProcessor.execute(command);
    }
}
