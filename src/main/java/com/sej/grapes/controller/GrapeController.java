package com.sej.grapes.controller;

import com.sej.grapes.dto.GrapeDto;
import com.sej.grapes.service.GrapeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grapes/{bunchGrapesId}/{grapeId}")
@Slf4j
@AllArgsConstructor
public class GrapeController {

    private GrapeService grapeService;

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void changeGrapeContent(@PathVariable Long grapeId,
                                   @RequestParam(required = false) String title, @RequestParam(required = false) String content){
        grapeService.changeContent(grapeId, title, content);
    }

    @PatchMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Long changeCheckedStatus(@PathVariable Long grapeId){
        grapeService.changeCheckedStatus(grapeId);
        return grapeId;
    }
}
