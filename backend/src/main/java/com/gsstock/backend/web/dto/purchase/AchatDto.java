package com.gsstock.backend.web.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AchatDto(
        Long id,
        String referenceFacture,
        LocalDate date,
        BigDecimal tvaRate,
        String status,
        BigDecimal totalHT,
        BigDecimal totalTVA,
        BigDecimal totalTTC,
        Long fournisseurId,
        List<AchatLineDto> lines
) {}
