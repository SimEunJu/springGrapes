package com.sej.grapes.controller;

import com.sej.grapes.dto.GrapeChkResDto;
import com.sej.grapes.dto.GrapeDto;
import com.sej.grapes.model.Grape;
import com.sej.grapes.service.GrapeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grapes/{bunchGrapesId}/grape/{grapeId}")
@Slf4j
@AllArgsConstructor
public class GrapeController {

    private GrapeService grapeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GrapeDto> getGrapeContent(@PathVariable Long grapeId) {
        Grape grape = grapeService.getGrape(grapeId);
        GrapeDto grapteDTo = GrapeDto.convertToDto(grape);
        return ResponseEntity.ok(grapteDTo);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GrapeDto> changeGrapeContent(@PathVariable Long grapeId,
                                                       @RequestBody GrapeDto grapeDto) {
        Grape grape = grapeService.changeContent(grapeId, grapeDto);
        GrapeDto updatedGrapteDTo = GrapeDto.convertToDto(grape);
        return ResponseEntity.ok(updatedGrapteDTo);
    }

    @PatchMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public GrapeChkResDto changeCheckedStatus(@PathVariable long grapeId, @RequestBody GrapeDto grapeDto) {
        grapeDto.setId(grapeId);
        grapeService.changeCheckedStatus(grapeDto);
        return GrapeChkResDto.builder()
                .isChecked(grapeDto.isChecked())
                .seq(grapeDto.getSeq())
                .id(grapeDto.getId())
                .build();
    }
}
