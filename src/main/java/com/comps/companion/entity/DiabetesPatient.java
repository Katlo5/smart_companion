package com.comps.companion.entity;

import jakarta.persistence.*;
import lombok.*;   
import java.time.LocalDateTime; 

@Entity
@Table(name="diabetes_patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiabetesPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private Double glucoseLevel;

    private Double hba1c;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    
    @PrePersist
    protected void onCreate() {
        if (this.measuredAt == null) {
            this.measuredAt = LocalDateTime.now();
        }
    }
    
    @Column
    private Double predictedNextValue;

    @Column
    private String riskAssessment;
}