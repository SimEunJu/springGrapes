package com.sej.grapes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
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
}
