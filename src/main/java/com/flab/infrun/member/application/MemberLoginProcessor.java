package com.flab.infrun.member.application;

import com.flab.infrun.member.application.command.LoginCommand;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import com.flab.infrun.member.infrastructure.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class MemberLoginProcessor {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String execute(final LoginCommand command) {
        verifyCommand(command);

        final var authenticationToken = new UsernamePasswordAuthenticationToken(
            command.email(), command.password());
        final var authentication = authenticationManagerBuilder.getObject()
            .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    private void verifyCommand(final LoginCommand command) {
        if (!StringUtils.hasText(command.email())) {
            throw new NotFoundMemberException();
        }

        final var member = memberRepository.findByEmail(command.email());

        member.isMatchPassword(passwordEncoder.encode(command.password()));
    }
}
