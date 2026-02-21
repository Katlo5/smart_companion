package com.comps.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comps.companion.entity.DiabetesPatient;
import com.comps.companion.repository.DiabetesRepository;
@Service
public class DiabetesService {

    @Autowired
    private DiabetesRepository diabetesRepository;

    public DiabetesPatient saveDiabetesPatient(DiabetesPatient patient) {
        return diabetesRepository.save(patient);
    }
}