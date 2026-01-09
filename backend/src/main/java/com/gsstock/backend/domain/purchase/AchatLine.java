package com.gsstock.backend.domain.purchase;

import com.gsstock.backend.domain.stock.Produit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AchatLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal quantite;
    private BigDecimal prixUnitaireHT;

    private BigDecimal totalHT;
    private BigDecimal totalTVA;
    private BigDecimal totalTTC;

    @ManyToOne(optional = false)
    private Produit produit;

    @ManyToOne(optional = false)
    private Achat achat;
}
