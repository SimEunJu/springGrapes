package com.sej.grapes.controller;

import com.sej.grapes.dto.BunchGrapesDto;
import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.service.BunchGrapesService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/grapes")
@AllArgsConstructor
@Slf4j
public class BunchGrapesController {

    private BunchGrapesService bunchGrapesService;

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createNewGrapes(@AuthenticationPrincipal MemberDto memberDto,
                                            @RequestBody Map<String, Integer> json){
        int depth = json.get("depth");
        Long bunchGrapesId = bunchGrapesService.create(memberDto.getEmail(), depth);
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

    @PatchMapping("/{bunchGrapesId}/rgba")
    @ResponseStatus(HttpStatus.OK)
    public void changeRgba(@PathVariable Long bunchGrapesId, @RequestParam String rgba){
        bunchGrapesService.updateRgba(bunchGrapesId, rgba);
    }

    @PatchMapping("/{bunchGrapesId}/title")
    @ResponseStatus(HttpStatus.OK)
    public String changeTitle(@PathVariable Long bunchGrapesId, @RequestBody @NonNull Map<String, String> json){
        String title = json.get("title");
        bunchGrapesService.updateTitle(bunchGrapesId, title);
        return title;
    }

    @PatchMapping("/{bunchGrapesId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void changeFinishStatus(@PathVariable Long bunchGrapesId){
        bunchGrapesService.updateFinishState(bunchGrapesId);
    }



}
