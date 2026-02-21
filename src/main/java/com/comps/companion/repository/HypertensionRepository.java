package com.comps.companion.repository;

import com.comps.companion.entity.HypertensionPatient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends CrudRepository<HypertensionPatient, Long> {
    
}