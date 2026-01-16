package com.gsstock.backend.web.dto.stats;

import java.math.BigDecimal;

public record TopProduitDto(
        Long produitId,
        String designation,
        BigDecimal totalSortie
) {}
