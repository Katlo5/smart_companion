package com.comps.companion.repository;

import com.comps.companion.entity.Clinitian;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface ClinitianRepository extends JpaRepository<Clinitian, Long> {
    
    Optional<Clinitian> findBySupabaseUserId(String supabaseUserId);
}