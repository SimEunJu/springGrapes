package com.sej.grapes.service;

import com.sej.grapes.dto.BunchGrapesDto;
import com.sej.grapes.error.exception.NoSuchResourceException;
import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.model.Grape;
import com.sej.grapes.model.Member;
import com.sej.grapes.repository.BunchGrapesRepository;
import com.sej.grapes.repository.GrapeRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Transactional
public class BunchGrapesService {

    private BunchGrapesRepository bunchGrapesRepository;
    private GrapeRepository grapeRepository;
    private ModelMapper mapper;

    public Long create(String memberEmail, int grapesDepth){
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

        int totalCnt = (grapesDepth*(grapesDepth+1)) / 2;

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

    public BunchGrapesDto getBunchGrapesById(long bunchGrapesId){
        // 어떻게 가져올 것인가
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        return BunchGrapesDto.convertToDto(bunchGrapes);
    }

    public void delete(long bunchGrapesId){
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setIsDelete(true);
        bunchGrapes.setDeleteDate(LocalDateTime.now());
        //bunchGrapesRepository.save(bunchGrapes);
    }

    public void updateRgba(long bunchGrapesId, String rgba){
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setRgba(rgba);
        //bunchGrapesRepository.save(bunchGrapes);
    }

    public void finish(long bunchGrapesId, String rgba){
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setRgba(rgba);
        bunchGrapes.setIsFinished(true);
        bunchGrapes.setFinishDate(LocalDateTime.now());
        //bunchGrapesRepository.save(bunchGrapes);
    }

    public void updateTitle(long bunchGrapesId, String title){
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setTitle(title);
        //bunchGrapesRepository.save(bunchGrapes);
    }

    public void updateFinishState(long bunchGrapesId){
        BunchGrapes bunchGrapes = findBunchGrapesById(bunchGrapesId);
        bunchGrapes.setIsFinished(true);
        bunchGrapes.setFinishDate(LocalDateTime.now());
        //bunchGrapesRepository.save(bunchGrapes);
    }

    private BunchGrapes findBunchGrapesById(long bunchGrapesId){
        Optional<BunchGrapes> bunchGrapesEntity = bunchGrapesRepository.findById(bunchGrapesId);
        BunchGrapes bunchGrapes = bunchGrapesEntity
                .orElseThrow(() -> new NoSuchResourceException(bunchGrapesId + ": 해당 grapes는 존재하지 않습니다."));
        return bunchGrapes;
    }
}
