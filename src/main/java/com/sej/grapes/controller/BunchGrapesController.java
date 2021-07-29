package com.sej.grapes.controller;

import com.sej.grapes.dto.*;
import com.sej.grapes.dto.jwt.BunchGrapesUpdateResDto;
import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.service.BunchGrapesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/grapes")
@RequiredArgsConstructor
@Slf4j
@Api(description = "포도송이에 적용")
public class BunchGrapesController {

    private final BunchGrapesService bunchGrapesService;

    @ApiOperation(value = "새로운 포도송이를 만든다", response = BunchGrapesCreateResDto.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BunchGrapesCreateResDto createNewBunchGrapes(@ApiIgnore @AuthenticationPrincipal MemberDto memberDto,
                                                                    HttpServletResponse res,
                                                                    @RequestBody BunchGrapesCreateReqDto reqDto) {

        Long bunchGrapesId = bunchGrapesService.create(memberDto.getEmail(), reqDto.getDepth());
        String token = res.getHeader("Authorization").substring(7);

        BunchGrapesCreateResDto resDto = BunchGrapesCreateResDto.builder()
                .bunchGrapesId(bunchGrapesId)
                .token(token)
                .build();

        return resDto;
    }

    @ApiOperation(value = "포도송이를 불러온다", response = BunchGrapesDto.class)
    @GetMapping("/{bunchGrapesId}")
    @ResponseStatus(HttpStatus.OK)
    public BunchGrapesDto getBunchGrapes(@ApiParam("포도송이 아이디") @PathVariable long bunchGrapesId) {
        BunchGrapesDto bunchGrapesDto = bunchGrapesService.getBunchGrapesById(bunchGrapesId);
        return bunchGrapesDto;
    }

    @ApiOperation(value = "포도송이를 삭제한다")
    @DeleteMapping("/{bunchGrapesId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBunchGrapes(@ApiParam("포도송이 아이디") @PathVariable long bunchGrapesId) {
        bunchGrapesService.delete(bunchGrapesId);
    }

    @ApiOperation(value = "포도송이의 제목을 변경한다", response = BunchGrapesUpdateResDto.class)
    @PatchMapping("/{bunchGrapesId}/title")
    @ResponseStatus(HttpStatus.OK)
    public BunchGrapesUpdateResDto changeTitle(@ApiParam("포도송이 아이디") @PathVariable Long bunchGrapesId, @RequestBody BunchGrapesUpdateReqDto reqDto) {
        @NonNull String title = reqDto.getTitle();
        title = bunchGrapesService.updateTitle(bunchGrapesId, title);
        BunchGrapesUpdateResDto resDto = BunchGrapesUpdateResDto.builder().title(title).build();
        return resDto;
    }

    @ApiOperation(value = "포도송이를 완료 상태로 변경한다", response = BunchGrapesUpdateResDto.class)
    @PatchMapping("/{bunchGrapesId}/finish")
    @ResponseStatus(HttpStatus.OK)
    public BunchGrapesUpdateResDto changeFinishStatus(@ApiParam("포도송이 아이디") @PathVariable Long bunchGrapesId,
                                   @RequestBody BunchGrapesUpdateReqDto reqDto) {

        @NonNull String rgba = reqDto.getRgba();
        rgba = bunchGrapesService.updateFinishState(bunchGrapesId, rgba);
        BunchGrapesUpdateResDto resDto = BunchGrapesUpdateResDto.builder().rgba(rgba).build();
        return resDto;
    }

    @ApiOperation(value = "포도송이 리스트를 불러온다", response = PageResultDto.class)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResultDto getBunchGrapesList(@ApiIgnore @AuthenticationPrincipal MemberDto memberDto,
                                                            PageRequestDto pageRequestDto) {
        PageResultDto<BunchGrapesDto, BunchGrapes> pageResultDto
                = bunchGrapesService.getBunchGrapesList(memberDto.getEmail(), pageRequestDto);
        return pageResultDto;
    }

}
