package com.sej.grapes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrapeChkResDto {

    private long id;
    private int seq;

    @JsonProperty("isChecked")
    private boolean isChecked;

}
