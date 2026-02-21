package com.comps.companion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comps.companion.entity.DiabetesPatient;
import com.comps.companion.service.DiabetesService;
@RestController
@RequestMapping("/api/diabetes")
public class DiabetesController {

    @Autowired
    private DiabetesService diabetesService;

    @PostMapping("/readings")
    public ResponseEntity<DiabetesPatient> createDiabetesPatient(@RequestBody DiabetesPatient patient) {
        DiabetesPatient savedPatient = diabetesService.saveDiabetesPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
}