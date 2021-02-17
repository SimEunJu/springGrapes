package com.sej.grapes.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BunchGrapes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bunchGrapes")
    @Builder.Default
    private List<Grape> grapes = new ArrayList<>();

    @Column(columnDefinition = "boolean default false")
    private Boolean isFinished;
    private LocalDateTime finishDate;

    @Column(nullable = false)
    private Integer depth;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String rgba;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete;
    private LocalDateTime deleteDate;

}
