package com.sej.grapes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BunchGrapes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Member member;

    @Column(nullable = false)
    private Long memberId;

    @Column(columnDefinition = "boolean default false")
    private Boolean isFinished;
    private LocalDateTime finishDate;

    @Column(nullable = false)
    private Integer depth;

    @Column(length = 100)
    private String title;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete;
    private LocalDateTime deleteTime;

}
