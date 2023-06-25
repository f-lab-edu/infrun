package com.flab.infrun.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.common.config.jwt.JwtAccessDeniedHandler;
import com.flab.infrun.common.config.jwt.JwtAuthenticationEntryPoint;
import com.flab.infrun.common.config.jwt.JwtSecurityConfig;
import com.flab.infrun.member.infrastructure.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig(final TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // TODO : 스프링 시큐리티 설정을 더 적절하게 작성해야 함
        http
            .csrf(AbstractHttpConfigurer::disable)

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler()))

            .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))

            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers().permitAll()
                .requestMatchers("/h2-console", "/favicon.ico").permitAll()
                .requestMatchers("/members/**").permitAll()
                .anyRequest().authenticated()
            )

            .apply(jwtSecurityConfig());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtSecurityConfig jwtSecurityConfig() {
        return new JwtSecurityConfig(tokenProvider);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(new ObjectMapper());
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler(new ObjectMapper());
    }
}
