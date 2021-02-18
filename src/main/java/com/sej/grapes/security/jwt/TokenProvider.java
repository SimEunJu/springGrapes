package com.sej.grapes.security.jwt;

import com.sej.grapes.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds){
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

        MemberDto principal = new MemberDto(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // @reference http://javadox.com/io.jsonwebtoken/jjwt/0.4/io/jsonwebtoken/JwtParser.html#parseClaimsJws-java.lang.String-
    public boolean validateToken(String token){
        String errorMsg = "";
        Throwable cause = null;
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            //if the claimsJws string is not a valid JWS
            errorMsg = "올바른 JWT 형식이 아닙니다.";
            cause = e;
        }catch (ExpiredJwtException e){
            //if the specified JWT is a Claims JWT and the Claims has an expiration time before the time this method is invoked.
            errorMsg = "만료된 JWT 입니다.";
            cause = e;
        }catch (UnsupportedJwtException e){
            //if the claimsJws argument does not represent an Claims JWS
            errorMsg = "올바른 claim이 아닙니다.";
            cause = e;
        }catch (IllegalArgumentException e){
            //if the claimsJws string is null or empty or only whitespace
            errorMsg = "claim이 없습니다.";
            cause = e;
        }catch (SecurityException e){
            //if the plaintextJwt string is actually a JWS and signature validation fails
            errorMsg = "JWT 인증에 실패했습니다.";
            cause = e;
        }
        log.error(errorMsg);
        //throw new AccessDeniedException(errorMsg, cause);
        return false;
    }
}
