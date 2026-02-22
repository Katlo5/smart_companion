package com.comps.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comps.companion.entity.HypertensionPatient;
import com.comps.companion.repository.HypertensionRepository;
import java.util.List;
import java.util.Optional;
@Service
public class HypertensionService {

    @Autowired
    private HypertensionRepository hypertensionRepository;

    public HypertensionPatient saveHypertensionPatient(HypertensionPatient patient) {
        return hypertensionRepository.save(patient);
    }

    public List<HypertensionPatient> getAllReadings() {
        return hypertensionRepository.findAll();
    }

    public Optional<HypertensionPatient> getReadingById(Long id) {
        return hypertensionRepository.findById(id);
    }
}

