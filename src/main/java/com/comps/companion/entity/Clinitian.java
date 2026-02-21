package com.comps.companion.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="clinitians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clinitian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String supabaseUserId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private String specialization;
}