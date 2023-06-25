package com.flab.infrun.common.config.jwt;

import com.flab.infrun.member.infrastructure.jwt.TokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    public JwtSecurityConfig(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(final HttpSecurity builder) throws Exception {
        final JwtFilter filter = new JwtFilter(tokenProvider);

        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
