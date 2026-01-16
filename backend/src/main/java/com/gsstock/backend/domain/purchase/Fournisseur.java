package com.gsstock.backend.domain.purchase;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String ice;
    private String telephone;
    private String email;

    @Column(length = 500)
    private String adresse;

    @Column(nullable = false)
    private boolean active = true;
}
