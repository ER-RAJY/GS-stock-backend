package com.gsstock.backend.domain.stock;

import com.gsstock.backend.domain.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(nullable = false)
    private BigDecimal quantite;

    private LocalDate date;

    private String commentaire;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    private Produit produit;

    @ManyToOne
    private User createdBy;
}
