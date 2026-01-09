package com.gsstock.backend.domain.purchase;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Achat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceFacture;

    private LocalDate date;

    private BigDecimal tvaRate;

    @Enumerated(EnumType.STRING)
    private AchatStatus status = AchatStatus.DRAFT;

    private BigDecimal totalHT;
    private BigDecimal totalTVA;
    private BigDecimal totalTTC;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(optional = false)
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "achat", cascade = CascadeType.ALL)
    private List<AchatLine> lines;
}
