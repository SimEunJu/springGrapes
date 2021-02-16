package com.sej.grapes.service;

import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.model.Member;
import com.sej.grapes.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private MemberRepository memberRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberByEmail = memberRepository.findMemberByEmail(username);
        Member member = memberByEmail.orElseThrow(() -> new UsernameNotFoundException(username+": 일치하는 회원이 없습니다."));
        Collection<GrantedAuthority> authorities = member.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList());
        MemberDto memberDto = new MemberDto(member.getEmail(), member.getPassword(), authorities);
        return memberDto;
    }
}
