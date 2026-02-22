package com.comps.companion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.comps.companion.entity.HypertensionPatient;
import com.comps.companion.service.HypertensionService;
import java.util.List;
import java.util.Optional;  

@RestController
@RequestMapping("/api/hypertension")
public class HypertensionController {

    @Autowired
    private HypertensionService hypertensionService;

  @PostMapping("/readings")
public ResponseEntity<?> createHypertensionPatient(@RequestBody HypertensionPatient patient) {
    try {
        HypertensionPatient savedPatient = hypertensionService.saveHypertensionPatient(patient);
        return ResponseEntity.ok(savedPatient);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}


    @GetMapping("/readings")
    public ResponseEntity<List<HypertensionPatient>> getAllReadings() {
        return ResponseEntity.ok(hypertensionService.getAllReadings());
    }

    
    @GetMapping("/readings/{id}")
    public ResponseEntity<HypertensionPatient> getReadingById(@PathVariable Long id) {
        return hypertensionService.getReadingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}