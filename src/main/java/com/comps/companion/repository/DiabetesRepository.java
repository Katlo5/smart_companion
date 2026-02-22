package com.comps.companion.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import com.comps.companion.entity.DiabetesPatient;
import org.springframework.stereotype.Repository;

@Repository
public interface DiabetesRepository extends JpaRepository<DiabetesPatient, Long> {
}