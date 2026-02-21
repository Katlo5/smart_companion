package com.comps.companion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comps.companion.entity.HypertensionPatient;
import com.comps.companion.service.HypertensionService;
@RestController
@RequestMapping("/api/hypertension")
public class HypertensionController {

    @Autowired
    private HypertensionService hypertensionService;

    @PostMapping("/readings")
    public ResponseEntity<HypertensionPatient> createHypertensionPatient(@RequestBody HypertensionPatient patient) {
        HypertensionPatient savedPatient = hypertensionService.saveHypertensionPatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
}