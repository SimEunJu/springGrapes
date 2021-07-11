package com.sej.grapes.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class PageResultDto<Dto, En> {

    private List<Dto> dtoList;

    private int totalPages;
    private int page;
    private int size;
    private boolean hasNext;

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
