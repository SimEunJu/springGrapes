package com.sej.grapes.controller;

import com.sej.grapes.dto.BunchGrapesDto;
import com.sej.grapes.dto.MemberDto;
import com.sej.grapes.dto.PageRequestDto;
import com.sej.grapes.dto.PageResultDto;
import com.sej.grapes.dto.jwt.TokenDto;
import com.sej.grapes.error.ErrorCode;
import com.sej.grapes.error.exception.JwtAuthenticationException;
import com.sej.grapes.error.exception.JwtTokenExpireException;
import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.security.jwt.JwtFilter;
import com.sej.grapes.security.jwt.TokenProvider;
import com.sej.grapes.service.BunchGrapesService;
import com.sej.grapes.service.MemberDetailsService;
import com.sej.grapes.service.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/grapes")
@RequiredArgsConstructor
@Slf4j
public class BunchGrapesController {

    private final BunchGrapesService bunchGrapesService;

    private final MemberDetailsService memberDetailsService;

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> createNewBunchGrapes(@AuthenticationPrincipal MemberDto memberDto,
                                                                    HttpServletResponse res,
                                                                    @RequestBody Map<String, Integer> json) {
        int depth = json.get("depth");

        Long bunchGrapesId = bunchGrapesService.create(memberDto.getEmail(), depth);
        String token = res.getHeader("Authorization").substring(7);
        Map<String, Object> response = new HashMap<>();
        response.put("bunchGrapesId", bunchGrapesId);
        response.put("token", token);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{bunchGrapesId}")
    public ResponseEntity<BunchGrapesDto> getBunchGrapes(@PathVariable Long bunchGrapesId) {
        BunchGrapesDto bunchGrapesDto = bunchGrapesService.getBunchGrapesById(bunchGrapesId);
        return ResponseEntity.ok(bunchGrapesDto);
    }

    @DeleteMapping("/{bunchGrapesId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBunchGrapes(@PathVariable Long bunchGrapesId) {
        bunchGrapesService.delete(bunchGrapesId);
    }

    @PatchMapping("/{bunchGrapesId}/rgba")
    @ResponseStatus(HttpStatus.OK)
    public void changeRgba(@PathVariable Long bunchGrapesId, @RequestParam String rgba) {
        bunchGrapesService.updateRgba(bunchGrapesId, rgba);
    }

    @PatchMapping("/{bunchGrapesId}/title")
    @ResponseStatus(HttpStatus.OK)
    public String changeTitle(@PathVariable Long bunchGrapesId, @RequestBody @NonNull Map<String, String> json) {
        String title = json.get("title");
        bunchGrapesService.updateTitle(bunchGrapesId, title);
        return title;
    }

    @PatchMapping("/{bunchGrapesId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> changeFinishStatus(@PathVariable Long bunchGrapesId,
                                   @RequestBody Map<String, String> json) {
        @NonNull String rgba = json.get("rgba");
        bunchGrapesService.updateFinishState(bunchGrapesId, rgba);
        Map<String, String> res = new HashMap<>();
        res.put("rgba", rgba);
        return res;
    }


    @GetMapping("/list")
    public ResponseEntity<PageResultDto> getBunchGrapesList(@AuthenticationPrincipal MemberDto memberDto,
                                                            PageRequestDto pageRequestDto) {
        PageResultDto<BunchGrapesDto, BunchGrapes> pageResultDto
                = bunchGrapesService.getBunchGrapesList(memberDto.getEmail(), pageRequestDto);
        return ResponseEntity.ok(pageResultDto);
    }

}
