package com.comps.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.comps.companion.entity.Clinitian;
import com.comps.companion.repository.ClinitianRepository;
@Service
public class ClinitianService {

    @Autowired
    private ClinitianRepository clinitianRepository;

    public Clinitian saveClinitian(Clinitian clinitian) {
        return clinitianRepository.save(clinitian);
    }

    public Clinitian findBySupabaseUserId(String supabaseUserId) {
        return clinitianRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("Clinitian not found with Supabase User ID: " + supabaseUserId));
    }

    public Clinitian updateProfile(String supabaseId, Clinitian updatedClinitian) {
        Clinitian existingClinitian = clinitianRepository.findBySupabaseUserId(supabaseId)
                .orElseThrow(() -> new RuntimeException("Clinitian not found"));

        existingClinitian.setName(updatedClinitian.getName());
        existingClinitian.setEmail(updatedClinitian.getEmail());
        existingClinitian.setSpecialization(updatedClinitian.getSpecialization());

        return clinitianRepository.save(existingClinitian);
    }
}