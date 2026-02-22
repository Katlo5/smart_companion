package com.comps.companion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comps.companion.entity.DiabetesPatient;
import com.comps.companion.service.DiabetesService;
import com.comps.companion.service.PredictiveAnalysisService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/diabetes")
public class DiabetesController {

    @Autowired
    private DiabetesService diabetesService;

    @Autowired
    private PredictiveAnalysisService predictiveAnalysisService;

    @PostMapping("/readings")
    public ResponseEntity<DiabetesPatient> createDiabetesPatient(@RequestBody DiabetesPatient patient) {
        DiabetesPatient savedPatient = diabetesService.saveDiabetesPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }

    @GetMapping("/readings")
    public ResponseEntity<List<DiabetesPatient>> getAllDiabetesPatients() {
        return ResponseEntity.ok(diabetesService.getAllPatients());
    }

    @GetMapping("/readings/{id}")
    public ResponseEntity<DiabetesPatient> getDiabetesPatient(@PathVariable Long id) {
        DiabetesPatient patient = diabetesService.getDiabetesPatientById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        // Run Weka AI prediction using all available glucose readings as history
        try {
            List<DiabetesPatient> all = diabetesService.getAllPatients();
            ArrayList<Double> history = new ArrayList<>();
            for (DiabetesPatient p : all) {
                history.add(p.getGlucoseLevel());
            }
            Double predicted = predictiveAnalysisService.predictNextValue(history);
            if (predicted != null) {
                patient.setPredictedNextValue(predicted);
                double g = patient.getGlucoseLevel();
                String risk;
                if (g > 180)       risk = "Critical — immediate clinical review required";
                else if (g > 126)  risk = "High — glucose above diabetic threshold";
                else if (g > 100)  risk = "Borderline — pre-diabetic range, monitor closely";
                else               risk = "Normal — glucose within healthy range";
                patient.setRiskAssessment(risk);
                diabetesService.saveDiabetesPatient(patient); // persist the AI results
            }
        } catch (Exception e) {
            patient.setRiskAssessment("AI analysis unavailable");
        }

        return ResponseEntity.ok(patient);
    }
}