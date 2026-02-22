package com.comps.companion.entity;

import jakarta.persistence.*; 
import lombok.*;         
import java.time.LocalDateTime;

@Entity
@Table(name="hypertension_patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HypertensionPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientName;

    @Column(nullable = false)
    private Integer systolic;

    @Column(nullable = false)
    private Integer diastolic;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    @PrePersist
    protected void onCreate() {
        this.measuredAt = LocalDateTime.now();
    }
}