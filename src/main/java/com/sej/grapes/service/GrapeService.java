package com.sej.grapes.service;

import com.sej.grapes.error.exception.NoSuchResourceException;
import com.sej.grapes.model.BunchGrapes;
import com.sej.grapes.model.Grape;
import com.sej.grapes.repository.GrapeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GrapeService {

    private GrapeRepository grapeRepository;
    private ModelMapper modelMapper;

    public void changeContent(long grapeId, String title, String content){
        Grape grape = findGrapeById(grapeId);
        grape.setTitle(title);
        grape.setContent(content);
        grapeRepository.save(grape);
    }

    public void changeCheckedStatus(long grapeId){
        Grape grape = findGrapeById(grapeId);
        grape.setChecked(true);
        grape.setCheckedDate(LocalDateTime.now());
        grapeRepository.save(grape);
    }

    private Grape findGrapeById(long grapeId){
        Optional<Grape> grapeEntity = grapeRepository.findById(grapeId);
        Grape grape = grapeEntity
                .orElseThrow(() -> new NoSuchResourceException(grapeId + ": 해당 grapes는 존재하지 않습니다."));
        return grape;
    }
}
