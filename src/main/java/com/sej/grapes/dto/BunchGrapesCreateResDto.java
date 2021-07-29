package com.sej.grapes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BunchGrapesCreateResDto {

    @ApiModelProperty("생성된 포도송이 아이디")
    long bunchGrapesId;
    @ApiModelProperty("포도송이 생성시 일괄적으로 발급한 토큰")
    String token;
}
