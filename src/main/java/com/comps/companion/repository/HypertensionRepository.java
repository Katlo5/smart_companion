package com.comps.companion.repository;

import com.comps.companion.entity.HypertensionPatient;
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends JpaRepository<HypertensionPatient, Long> {
   
}