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

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private SocialLogin socialLogin;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
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
