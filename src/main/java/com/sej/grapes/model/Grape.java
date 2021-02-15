package com.sej.grapes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private BunchGrapes bunchGrapes;

    @LastModifiedDate
    private LocalDate updateDate;

    @Column(columnDefinition = "boolean default false")
    private boolean isChecked;

    private String title;

    @Lob
    private String content;
}
