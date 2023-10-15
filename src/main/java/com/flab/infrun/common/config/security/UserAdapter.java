package com.flab.infrun.common.config.security;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.Role;
import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserAdapter extends User {

    private final Member member;

    public UserAdapter(final Member member) {
        super(member.getEmail(), member.getPassword(), authorities(member.getRole()));
        this.member = member;
    }

    private static Collection<? extends GrantedAuthority> authorities(final Role role) {
        return Set.of(new SimpleGrantedAuthority(role.value));
    }

    public Member getMember() {
        return member;
    }
}
