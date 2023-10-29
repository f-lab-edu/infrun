//package com.flab.infrun.common.config.security;
//
//import com.flab.infrun.common.config.jwt.JwtSecurityConfig;
//import com.flab.infrun.member.infrastructure.jwt.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    private final TokenProvider tokenProvider;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
//
//    @Bean
//    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
//        // TODO : 스프링 시큐리티 설정을 더 적절하게 작성해야 함
//        http
//            .csrf(AbstractHttpConfigurer::disable)
//
//            .exceptionHandling(handler -> handler
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .accessDeniedHandler(customAccessDeniedHandler))
//
//            .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
//
//            .sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//            .authorizeHttpRequests(request -> request
//                .requestMatchers("/members/**").permitAll()
//                .requestMatchers("/lecture/**").permitAll()
//                .anyRequest().authenticated()
//            )
//
//            .apply(jwtSecurityConfig());
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public JwtSecurityConfig jwtSecurityConfig() {
//        return new JwtSecurityConfig(tokenProvider);
//    }
//}
