package com.gsstock.backend.domain.stock;

import com.gsstock.backend.domain.auth.Unite;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String designation;

    @Enumerated(EnumType.STRING)
    private Unite unite;

    private BigDecimal stockMin;

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}
