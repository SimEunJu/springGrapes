package com.sej.grapes.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sej.grapes.dto.*;
import com.sej.grapes.error.exception.NoSuchResourceException;
import com.sej.grapes.model.*;
import com.sej.grapes.repository.BunchGrapesRepository;
import com.sej.grapes.repository.GrapeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class BunchGrapesService {

    private final BunchGrapesRepository bunchGrapesRepository;
    private final GrapeRepository grapeRepository;
    private final ModelMapper mapper;

    public Long create(String memberEmail, int grapesDepth) {
        // TODO: security context에 있는 MemberDto를 활용할 수는 없을까
        Member member = Member.builder()
                .email(memberEmail)
                .build();

        BunchGrapes bunchGrapes = BunchGrapes.builder()
                .member(member)
                .depth(grapesDepth)
                .isDelete(false)
                .isFinished(false)
                .build();
        bunchGrapesRepository.save(bunchGrapes);

        int totalCnt = (grapesDepth * (grapesDepth + 1)) / 2;

        IntStream.range(0, totalCnt).forEach((idx) -> {
            Grape grape = Grape.builder()
                    .seq(idx)
                    .bunchGrapes(bunchGrapes)
                    .isChecked(false)
                    .build();
            grapeRepository.save(grape);
        });

        return bunchGrapes.getId();
    }

    public BunchGrapesDto getBunchGrapesById(long bunchGrapesId) {
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        BunchGrapesDto bunchGrapesDto = BunchGrapesDto.convertToDto(bunchGrapes);
        List<Grape> grapes = bunchGrapes.getGrapes();
        List<GrapeDto> grapesDto = BunchGrapesDto.convertGrapeListToDto(grapes);
        bunchGrapesDto.setGrapes(grapesDto);
        return bunchGrapesDto;
    }

    public void delete(long bunchGrapesId) {
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setDelete(true);
        bunchGrapes.setDeleteDate(LocalDateTime.now());
        //bunchGrapesRepository.save(bunchGrapes);
    }

    public String updateRgba(long bunchGrapesId, String rgba) {
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setRgba(rgba);
        bunchGrapes =  bunchGrapesRepository.save(bunchGrapes);
        return bunchGrapes.getRgba();
    }

    public String updateTitle(long bunchGrapesId, String title) {
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setTitle(title);
        bunchGrapes = bunchGrapesRepository.save(bunchGrapes);
        return bunchGrapes.getTitle();
    }

    public String updateFinishState(long bunchGrapesId, String rgba) {
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setRgba(rgba);
        bunchGrapes.setFinished(true);
        bunchGrapes.setFinishDate(LocalDateTime.now());
        bunchGrapes = bunchGrapesRepository.save(bunchGrapes);
        return bunchGrapes.getRgba();
    }

    private BunchGrapes findBunchGrapesById(long bunchGrapesId) {
        Optional<BunchGrapes> bunchGrapesEntity = bunchGrapesRepository.findById(bunchGrapesId);
        BunchGrapes bunchGrapes = bunchGrapesEntity
                .orElseThrow(() -> new NoSuchResourceException(bunchGrapesId + ": 해당 grapes는 존재하지 않습니다."));
        return bunchGrapes;
    }

    public PageResultDto<BunchGrapesDto, BunchGrapes> getBunchGrapesList(String memberEmail, PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.getPageable(Sort.by("id").descending());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBunchGrapes qBunchGrapes = QBunchGrapes.bunchGrapes;

        BooleanExpression notDelete = qBunchGrapes.isDelete.eq(false);

        Member member = Member.builder().email(memberEmail).build();
        BooleanExpression sameEmail = qBunchGrapes.member.eq(member);

        booleanBuilder.and(notDelete.and(sameEmail));

        Page<BunchGrapes> result = bunchGrapesRepository.findAll(booleanBuilder, pageable);
        Function<BunchGrapes, BunchGrapesDto> fn = BunchGrapesDto::convertToDto;

        return new PageResultDto<>(result, fn);
    }

}
