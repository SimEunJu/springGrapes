package com.sej.grapes.controller;

import com.sej.grapes.dto.BunchGrapesDto;
import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.model.Member;
import com.sej.grapes.service.BunchGrapesService;
import com.sej.grapes.service.GrapeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grapes")
@AllArgsConstructor
@Slf4j
public class BunchGrapesController {

    private BunchGrapesService bunchGrapesService;

    @PostMapping("/new")
    public ResponseEntity<Long> createNewGrapes(@AuthenticationPrincipal MemberDto memberDto,
                                            @RequestParam Integer depth){
        Long bunchGrapesId = bunchGrapesService.create(memberDto.getId(), depth);
        return ResponseEntity.ok(bunchGrapesId);
    }

    @GetMapping("/{bunchGrapesId}")
    public ResponseEntity<BunchGrapesDto> getBunchGrapes(@PathVariable Long bunchGrapesId){
        BunchGrapesDto bunchGrapesDto = bunchGrapesService.getBunchGrapesById(bunchGrapesId);
        return ResponseEntity.ok(bunchGrapesDto);
    }

    @DeleteMapping("/{bunchGrapesId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBunchGrapes(@PathVariable Long bunchGrapesId){
        bunchGrapesService.delete(bunchGrapesId);
    }

    @PostMapping("/{bunchGrapesId}/rgba")
    @ResponseStatus(HttpStatus.OK)
    public void changeRgba(@PathVariable Long bunchGrapesId, @RequestParam String rgba){
        bunchGrapesService.updateRgba(bunchGrapesId, rgba);
    }

    @PostMapping("/{bunchGrapesId}/title")
    @ResponseStatus(HttpStatus.OK)
    public void changeTitle(@PathVariable Long bunchGrapesId, @RequestParam String title){
        bunchGrapesService.updateTitle(bunchGrapesId, title);
    }

    @PostMapping("/{bunchGrapesId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void changeFinishStatus(@PathVariable Long bunchGrapesId){
        bunchGrapesService.updateFinishState(bunchGrapesId);
    }



}
