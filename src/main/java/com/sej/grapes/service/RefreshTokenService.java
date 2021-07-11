package com.sej.grapes.service;

import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.dto.jwt.RefreshTokenDto;
import com.sej.grapes.error.exception.JwtTokenExpireException;
import com.sej.grapes.error.exception.TokenRefreshException;
import com.sej.grapes.model.Member;
import com.sej.grapes.model.RefreshToken;
import com.sej.grapes.repository.MemberRepository;
import com.sej.grapes.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Value("${jwt.refresh.expire-s}")
    private int refreshTokenExpireS;

    public int getRefreshTokenExpireS(){
        return this.refreshTokenExpireS;
    }

    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public String createRefreshToken(String email) {
        deleteByUserEmail(email);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .member(memberRepository.findMemberByEmail(email).get())
                .expiryDate(Instant.now().plusSeconds(refreshTokenExpireS))
                .build();

        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public RefreshToken verifyExpiration(String token) {
        RefreshToken refreshToken = findByToken(token).get();
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new JwtTokenExpireException("Refresh token was expired.");
        }

        return refreshToken;
    }

    public Authentication getAuthentication(RefreshToken refreshToken) {
        Member member = refreshToken.getMember();
        Collection<GrantedAuthority> authorities =
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList());

        MemberDto principal = new MemberDto(member.getEmail(), member.getPassword(), authorities);

        return new UsernamePasswordAuthenticationToken(principal, refreshToken.getToken(), authorities);
    }

    private int deleteByUserEmail(String email) {
        return refreshTokenRepository.deleteByMember(memberRepository.findMemberByEmail(email).get());
    }
}
