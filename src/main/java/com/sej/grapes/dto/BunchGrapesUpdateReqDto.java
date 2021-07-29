package com.sej.grapes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BunchGrapesUpdateReqDto {
    @ApiModelProperty("포도송이 주스 색상")
    private String rgba;
    @ApiModelProperty("포도송이 제목")
    private String title;
}
