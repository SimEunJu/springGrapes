package com.sej.grapes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sej.grapes.model.Grape;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrapeDto {

    @ApiModelProperty("포도알 아이디") private long id;
    @ApiModelProperty("포도알 순서") private int seq;

    @JsonProperty("isChecked")
    @ApiModelProperty("포도알 체크 여부") private boolean isChecked;

    @ApiModelProperty("포도알 제목") private String title;
    @ApiModelProperty("포도알 내용") private String content;

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
