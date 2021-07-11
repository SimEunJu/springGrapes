package com.sej.grapes.service;

import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.model.Member;
import com.sej.grapes.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(username);
        Member member = memberByEmail.orElseThrow(() -> new UsernameNotFoundException(username + ": 일치하는 회원이 없습니다."));
        Collection<GrantedAuthority> authorities = member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
        MemberDto memberDto = new MemberDto(member.getEmail(), member.getPassword(), authorities);
        return memberDto;
    }

    public Authentication getAuthentication(String username, String rawPassword) {

        UserDetails userDetails = loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
    }
}
