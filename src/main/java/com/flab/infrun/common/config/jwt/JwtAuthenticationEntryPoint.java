package com.flab.infrun.common.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.response.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
        final AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        final ServletOutputStream outputStream = response.getOutputStream();

        objectMapper.writeValue(outputStream, Response.fail(ErrorCode.UN_AUTHORIZATION));
    }
}
