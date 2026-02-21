package com.comps.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comps.companion.entity.HypertensionPatient;
import com.comps.companion.repository.HypertensionRepository;
@Service
public class HypertensionService {

    @Autowired
    private HypertensionRepository hypertensionRepository;

    public HypertensionPatient saveHypertensionPatient(HypertensionPatient patient) {
        return hypertensionRepository.save(patient);
    }
}

