package com.sej.grapes.model;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Grape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "int", nullable = false)
    private Integer seq;

    @ManyToOne(cascade = CascadeType.ALL)
    private BunchGrapes bunchGrapes;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private LocalDateTime checkedDate;

    @Column(columnDefinition = "boolean default false")
    private boolean isChecked;

    private String title;

    @Column(columnDefinition = "text")
    private String content;
}
