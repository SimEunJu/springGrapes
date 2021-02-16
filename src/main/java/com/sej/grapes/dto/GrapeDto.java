package com.sej.grapes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrapeDto {

    private long id;
    private int seq;
    private boolean isChecked;
    private String title;
    private String content;
}
