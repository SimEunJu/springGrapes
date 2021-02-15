package com.sej.grapes.repository;

import com.sej.grapes.model.Member;
import com.sej.grapes.model.constants.MemberRole;
import com.sej.grapes.model.constants.SocialLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class MemberInsertTest {

    @Autowired
    MemberRepository repo;
    @Autowired
    PasswordEncoder pwEncoder;

    @Test
    void insertMember(){
        Set<MemberRole> roleSet = Collections.singleton(MemberRole.USER);
        Member member = Member.builder()
                .email("test@gmail.com")
                .name("테스트")
                .socialLogin(SocialLogin.GOOGLE)
                .roleSet(roleSet)
                .build();
        repo.save(member);
    }
}
