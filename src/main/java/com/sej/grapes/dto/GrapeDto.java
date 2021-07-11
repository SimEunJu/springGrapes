package com.sej.grapes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sej.grapes.model.Grape;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrapeDto {

    private long id;
    private int seq;

    @Getter(AccessLevel.NONE)
    @JsonProperty("isChecked")
    private boolean isChecked;

    private String title;
    private String content;

    public static GrapeDto convertToDto(Grape grape) {
        return GrapeDto.builder()
                .id(grape.getId())
                .seq(grape.getSeq())
                .isChecked(grape.isChecked())
                .title(grape.getTitle())
                .content(grape.getContent())
                .build();
    }
}
