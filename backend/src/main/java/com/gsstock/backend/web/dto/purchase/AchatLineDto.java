package com.gsstock.backend.web.dto.purchase;

import java.math.BigDecimal;

public record AchatLineDto(
        Long id,
        Long produitId,
        BigDecimal quantite,
        BigDecimal prixUnitaireHT,
        BigDecimal totalHT,
        BigDecimal totalTVA,
        BigDecimal totalTTC
) {}
