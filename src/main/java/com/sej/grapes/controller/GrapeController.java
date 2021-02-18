package com.sej.grapes.controller;

import com.sej.grapes.dto.req.grape.GrapeDto;
import com.sej.grapes.model.Grape;
import com.sej.grapes.service.GrapeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/grapes/{bunchGrapesId}/{grapeId}")
@Slf4j
@AllArgsConstructor
public class GrapeController {

    private GrapeService grapeService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GrapeDto> changeGrapeContent(@PathVariable Long grapeId,
                                             @RequestBody GrapeDto grapeDto){
        Grape grape = grapeService.changeContent(grapeId, grapeDto);
        return ResponseEntity.ok(grapeDto);
    }

    @PatchMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Long changeCheckedStatus(@PathVariable Long grapeId){
        grapeService.changeCheckedStatus(grapeId);
        return grapeId;
    }
}
