package com.sej.grapes.dto;

import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.model.Grape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BunchGrapesDto{

    private long id;
    private int depth;
    private String title;
    private String rgba;
    private List<GrapeDto> grapes;

    // TODO: 분리해야 하나
    public static BunchGrapesDto convertToDto(BunchGrapes bunchGrapes){
        List<GrapeDto> grapes = new ArrayList<>();
        bunchGrapes.getGrapes().stream().forEach((grape) -> {
            GrapeDto grapeDto = GrapeDto.builder()
                .id(grape.getId())
                .seq(grape.getSeq())
                .isChecked(grape.isChecked())
                .build();
            grapes.add(grapeDto);
        });
        return BunchGrapesDto.builder()
                .depth(bunchGrapes.getDepth())
                .title(bunchGrapes.getTitle())
                .grapes(grapes)
                .build();
    }
}
