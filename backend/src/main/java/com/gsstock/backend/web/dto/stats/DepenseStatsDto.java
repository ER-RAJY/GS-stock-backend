package com.gsstock.backend.web.dto.stats;

import java.math.BigDecimal;

public record DepenseStatsDto(
        BigDecimal totalHT,
        BigDecimal totalTVA,
        BigDecimal totalTTC
) {}
