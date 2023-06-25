package com.flab.infrun.common.config;

import com.flab.infrun.member.domain.Member;
import com.flab.infrun.member.domain.MemberRepository;
import com.flab.infrun.member.domain.exception.NotFoundMemberException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
            .map(member -> createUser(email, member))
            .orElseThrow(NotFoundMemberException::new);
    }

    private User createUser(final String email, final Member member) {
        final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
            member.getRole().getValue()));

        return new User(email, member.getPassword(), authorities);
    }
}
