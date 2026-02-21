package com.comps.companion.entity;

import springframework.jarkata.persistence.*;

import javax.annotation.processing.Generated;

import lombok.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
