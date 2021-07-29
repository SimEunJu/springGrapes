package com.sej.grapes.controller;

import com.sej.grapes.dto.BunchGrapesCreateResDto;
import com.sej.grapes.dto.GrapeChkResDto;
import com.sej.grapes.dto.GrapeDto;
import com.sej.grapes.model.Grape;
import com.sej.grapes.service.GrapeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grapes/{bunchGrapesId}/grape/{grapeId}")
@Slf4j
@RequiredArgsConstructor
@Api(description = "포도알에 적용")
public class GrapeController {

    private final GrapeService grapeService;

    @ApiOperation(value = "포도알의 내용을 불러온다", response = GrapeDto.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GrapeDto getGrapeContent(@ApiParam("포도알 아이디") @PathVariable long grapeId) {
        Grape grape = grapeService.getGrape(grapeId);
        GrapeDto grapteDto = GrapeDto.convertToDto(grape);
        return grapteDto;
    }

    @ApiOperation(value = "포도알의 내용을 수정한다", response = GrapeDto.class)
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public GrapeDto changeGrapeContent(@ApiParam("포도알 아이디") @PathVariable long grapeId,
                                                       @RequestBody GrapeDto grapeDto) {
        Grape grape = grapeService.changeContent(grapeId, grapeDto);
        GrapeDto updatedGrapteDto = GrapeDto.convertToDto(grape);
        return updatedGrapteDto;
    }

    @ApiOperation(value = "포도알의 체크 여부를 수정한다", response = GrapeChkResDto.class)
    @PatchMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public GrapeChkResDto changeCheckedStatus(@ApiParam("포도알 아이디") @PathVariable long grapeId, @RequestBody GrapeDto grapeDto) {
        grapeDto.setId(grapeId);
        grapeService.changeCheckedStatus(grapeDto);
        return GrapeChkResDto.builder()
                .isChecked(grapeDto.isChecked())
                .seq(grapeDto.getSeq())
                .id(grapeDto.getId())
                .build();
    }
}
