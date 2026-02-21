package com.comps.companion.repository;

import com.comps.companion.entity.DiabetesPatient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DiabetesRepository extends CrudRepository<DiabetesPatient, Long> {
    
}