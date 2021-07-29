package com.sej.grapes.security.jwt;

import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.error.ErrorCode;
import com.sej.grapes.error.exception.JwtAuthenticationException;
import com.sej.grapes.error.exception.JwtTokenExpireException;
import com.sej.grapes.model.RefreshToken;
import com.sej.grapes.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;
    private RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        // 로그인 페이지 없어, 새로운 포도송이 만들 때 강제 로그인
        if("/api/grapes/new".equals(requestURI) && !StringUtils.hasText(jwt)){

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            MemberDto memberDto = new MemberDto("test@gmail.com", "1234", authorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberDto, "1234", authorities);

            String token = tokenProvider.createToken(authentication);
            ((HttpServletResponse) response).setHeader(AUTHORIZATION_HEADER, "Bearer " + token);

            String refreshToken = refreshTokenService.createRefreshToken(memberDto.getEmail());

            Cookie refreshCookie = new Cookie("refresh", refreshToken);
            //refreshCookie.setSecure(true);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(refreshTokenService.getRefreshTokenExpireS());
            refreshCookie.setPath("/");

            ((HttpServletResponse) response).addCookie(refreshCookie);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
            return;
        }

        if(!StringUtils.hasText(jwt)) {
            log.debug("유효한 JWT 토큰이 없음, uri: {}", requestURI);
            setAuthByRefresh((HttpServletRequest) request, (HttpServletResponse) response);
            chain.doFilter(request, response);
            return;
        }

        try{
            tokenProvider.validateToken(jwt);
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보 저장, uri: {}", authentication.getName(), requestURI);
        }
        catch (JwtTokenExpireException e){
            setAuthByRefresh((HttpServletRequest) request, (HttpServletResponse) response);
        }

        chain.doFilter(request, response);
    }

    private void setAuthByRefresh(HttpServletRequest request, HttpServletResponse response){
        RefreshToken refreshToken = checkRefreshToken(request);
        Authentication authentication = refreshTokenService.getAuthentication(refreshToken);
        String token = tokenProvider.createToken(authentication);
        response.setHeader(AUTHORIZATION_HEADER, "Bearer " + token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private RefreshToken checkRefreshToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) throw new JwtAuthenticationException("리프레시 토큰이 존재하지 않습니다.", ErrorCode.AUTHENTICATION_REQUIRED);
        Cookie refreshCookieFromReq =  Arrays.stream(cookies)
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .findFirst()
                .orElseGet( () -> { throw new JwtAuthenticationException("리프레시 토큰이 존재하지 않습니다.", ErrorCode.AUTHENTICATION_REQUIRED); } );
        return refreshTokenService.verifyExpiration(refreshCookieFromReq.getValue());
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
