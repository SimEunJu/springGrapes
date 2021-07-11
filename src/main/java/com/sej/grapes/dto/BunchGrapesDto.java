package com.sej.grapes.dto;

import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.model.Grape;
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

    private long id;
    private int depth;
    private String title;
    private String rgba;

    private List<GrapeDto> grapes;

    private LocalDateTime createDate;
    private LocalDateTime finishDate;

    // TODO: 이 친구는 GrapeDto에 있어야 하나
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

    // TODO: 분리해야 하나
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
