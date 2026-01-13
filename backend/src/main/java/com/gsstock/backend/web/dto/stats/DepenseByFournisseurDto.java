package com.gsstock.backend.web.dto.stats;

import java.math.BigDecimal;

public record DepenseByFournisseurDto(
        Long fournisseurId,
        String fournisseurNom,
        BigDecimal totalTTC
) {}
