package com.sej.grapes.model;

import com.sej.grapes.model.constants.MemberRole;
import com.sej.grapes.model.constants.SocialLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    private String email;

    private String password;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private SocialLogin socialLogin;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roleSet = new HashSet<>();

    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String nickname;

    @CreatedDate
    private LocalDateTime joinDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private LocalDateTime leaveDate;
}
