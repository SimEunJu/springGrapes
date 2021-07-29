package com.sej.grapes.dto;

import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.model.Grape;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NegativeOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BunchGrapesDto {

    @ApiModelProperty("포도송이 아이디") private long id;
    @ApiModelProperty("포도송이 줄 수") private int depth;
    @ApiModelProperty("포도송이 제목") private String title;
    @ApiModelProperty("포도송이 완료 시 주스 색상") private String rgba;

    @ApiModelProperty("포도알 리스트") private List<GrapeDto> grapes;

    @ApiModelProperty("포도송이 생성날짜") private LocalDateTime createDate;
    @ApiModelProperty("포도송이 완료날짜") private LocalDateTime finishDate;

    public static List<GrapeDto> convertGrapeListToDto(List<Grape> grapesList) {
        List<GrapeDto> grapes = new ArrayList<>();
        grapesList.stream().forEach((grape) -> {
            GrapeDto grapeDto = GrapeDto.builder()
                    .id(grape.getId())
                    .seq(grape.getSeq())
                    .isChecked(grape.isChecked())
                    .build();
            grapes.add(grapeDto);
        });
        return grapes;
    }

    public static BunchGrapesDto convertToDto(BunchGrapes bunchGrapes) {
        return BunchGrapesDto.builder()
                .id(bunchGrapes.getId())
                .depth(bunchGrapes.getDepth())
                .title(bunchGrapes.getTitle())
                .rgba(bunchGrapes.getRgba())
                .createDate(bunchGrapes.getCreateDate())
                .finishDate(bunchGrapes.getFinishDate())
                .build();
    }
}
