package com.comps.companion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import com.comps.companion.entity.Clinitian;
import com.comps.companion.service.ClinitianService;
import java.util.Optional;
@RestController
@RequestMapping("/api/clinitians")
public class ClinitianController {
    @Autowired
    private ClinitianService clinitianService;

    @PostMapping
    public ResponseEntity<Clinitian> createClinitian(@RequestBody Clinitian clinitian) {
        Clinitian savedClinitian = clinitianService.saveClinitian(clinitian);
        return ResponseEntity.ok(savedClinitian);
    }

    @PostMapping("/sync")
    public ResponseEntity<Clinitian> syncProfile(@AuthenticationPrincipal Jwt jwt, @RequestBody Clinitian clinitian) {
        String supabaseUserId = jwt.getSubject();
        clinitian.setSupabaseUserId(supabaseUserId);
        Clinitian savedClinitian = clinitianService.saveClinitian(clinitian);
        return ResponseEntity.ok(savedClinitian);
    }

    @GetMapping("/me")
    public ResponseEntity<Clinitian> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        Clinitian profile = clinitianService.findBySupabaseUserId(jwt.getSubject());
        return ResponseEntity.ok(profile);
    }

}