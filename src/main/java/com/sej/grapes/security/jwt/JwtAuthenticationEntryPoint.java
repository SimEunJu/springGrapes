package com.sej.grapes.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sej.grapes.error.ErrorCode;
import com.sej.grapes.error.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_FAIL);
        OutputStream out = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, errorResponse);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
