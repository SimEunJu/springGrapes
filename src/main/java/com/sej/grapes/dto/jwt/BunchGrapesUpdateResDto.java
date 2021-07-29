package com.sej.grapes.dto.jwt;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BunchGrapesUpdateResDto {
    @ApiModelProperty("포도송이 주스 색상")
    private String rgba;
    @ApiModelProperty("포도송이 제목")
    private String title;
}
