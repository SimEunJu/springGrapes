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
        // sendError를 상단에 위치시켜야 함
        // 아래에서 writeValue를 완료한 순간 response write가 완료되어 더 이상 쓸 수 없기 때문
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_FAIL);
        OutputStream out = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, errorResponse);
    }
}
