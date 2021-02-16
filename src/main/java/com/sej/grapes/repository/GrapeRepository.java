package com.sej.grapes.repository;

import com.sej.grapes.model.Grape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrapeRepository extends JpaRepository<Grape, Long> {

}
