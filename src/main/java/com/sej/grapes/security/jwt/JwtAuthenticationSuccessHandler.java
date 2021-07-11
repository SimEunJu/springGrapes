package com.sej.grapes.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.dto.jwt.TokenDto;
import com.sej.grapes.model.Member;
import com.sej.grapes.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private TokenProvider tokenProvider;
    private RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = tokenProvider.createToken(authentication);

        String email = ((MemberDto)authentication.getDetails()).getEmail();
        String refreshToken = refreshTokenService.createRefreshToken(email);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);

        Cookie refreshCookie = new Cookie("refresh", refreshToken);
        //refreshCookie.setSecure(true);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(refreshTokenService.getRefreshTokenExpireS());
        refreshCookie.setPath("/");

        response.addCookie(refreshCookie);

        TokenDto tokenDto = TokenDto.builder().accessToken(token)
                .refreshToken(refreshToken)
                .email(email)
                .build();

        objectMapper.writeValue(response.getOutputStream(), tokenDto);
    }
}
