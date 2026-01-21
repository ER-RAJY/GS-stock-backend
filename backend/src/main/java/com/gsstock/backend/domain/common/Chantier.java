package com.gsstock.backend.domain.common;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "chantier")
@Getter @Setter
public class Chantier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    private String adresse;

    private boolean active = true;

    private LocalDate dateDebut;
    private LocalDate dateFin;
}
