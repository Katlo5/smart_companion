package com.comps.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comps.companion.entity.DiabetesPatient;
import com.comps.companion.repository.DiabetesRepository;
import java.util.Random;
import java.time.LocalDateTime;
@Service
public class DataSimulationService {

    @Autowired
    private DiabetesRepository diabetesRepository;

    public void generateMockData(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            DiabetesPatient p = new DiabetesPatient();
            p.setPatientName("Mock Patient " + i);
            p.setMeasuredAt(LocalDateTime.now());

            if(random.nextDouble() < 0.3) {
                p.setGlucoseLevel(130 + random.nextDouble() * 50); // High glucose
            } else {
                p.setGlucoseLevel(70 + random.nextDouble() * 30); // Normal glucose
            }

            diabetesRepository.save(p);
        }

        System.out.println(">>> Success: H2 Database populated with " + count + " paitients");
    }
}