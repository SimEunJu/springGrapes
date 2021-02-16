package com.sej.grapes.model;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "int", nullable = false)
    private Integer seq;

    @ManyToOne
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
