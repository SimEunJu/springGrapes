package com.sej.grapes.repository;

import com.sej.grapes.model.BunchGrapes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BunchGrapesRepository
        extends JpaRepository<BunchGrapes, Long>, QuerydslPredicateExecutor<BunchGrapes> {
}
