package com.comps.companion.entity;

import javax.annotation.processing.Generated;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patients")
@Getters
@Setters
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private enum PatientCondition condition;
}