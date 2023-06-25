package com.flab.infrun.common.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.common.response.Response;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
        final AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        final ServletOutputStream outputStream = response.getOutputStream();

        objectMapper.writeValue(outputStream, Response.fail(ErrorCode.ACCESS_DENIED));
    }
}
