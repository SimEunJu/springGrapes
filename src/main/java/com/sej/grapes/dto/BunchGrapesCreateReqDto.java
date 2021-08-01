package com.sej.grapes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BunchGrapesCreateReqDto {
    
    @ApiModelProperty("포도송이가 몇 줄인지")
    private Integer depth;
}
