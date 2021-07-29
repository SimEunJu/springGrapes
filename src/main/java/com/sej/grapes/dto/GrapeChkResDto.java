package com.sej.grapes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrapeChkResDto {
    
    @ApiModelProperty("포도알 아이디") private long id;
    private int seq;

    @JsonProperty("isChecked")
    @ApiModelProperty("포도알 체크 여부") private boolean isChecked;

}
