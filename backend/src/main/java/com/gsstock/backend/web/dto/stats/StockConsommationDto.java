package com.gsstock.backend.web.dto.stats;

import java.math.BigDecimal;

public record StockConsommationDto(
        Long produitId,
        String designation,
        BigDecimal totalEntree,
        BigDecimal totalSortie,
        BigDecimal stockActuel
) {}
