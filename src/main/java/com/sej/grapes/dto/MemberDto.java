package com.sej.grapes.dto;

import com.sej.grapes.model.constants.SocialLogin;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Access;
import java.util.Collection;

@Getter
@Setter
public class MemberDto extends User {

    private String email;
    private SocialLogin socialLogin;

    public MemberDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
    }

    // username == id
    public MemberDto(String username, String password, SocialLogin socialLogin,
                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.socialLogin = socialLogin;
    }
}
