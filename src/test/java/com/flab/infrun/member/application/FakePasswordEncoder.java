package com.flab.infrun.member.application;

import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;

class FakePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return Objects.equals(rawPassword.toString(), encodedPassword);
    }
}
