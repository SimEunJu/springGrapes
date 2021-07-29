package com.sej.grapes.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PageResultDto<Dto, En> {

    @ApiModelProperty("리스트") private List<Dto> dtoList;

    @ApiModelProperty("총 페이지 수") private int totalPages;
    @ApiModelProperty("현재 페이지") private int page;
    @ApiModelProperty("페이지 크기") private int size;
    @ApiModelProperty("다음 페이지 존재 여부") private boolean hasNext;

    public PageResultDto(Page<En> result, Function<En, Dto> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPages = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();

        hasNext = page < totalPages;

    }

}
