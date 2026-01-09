package com.gsstock.backend.web.dto.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StockMovementDto(
        Long id,
        String type,
        BigDecimal quantite,
        LocalDate date,
        String commentaire,
        Long produitId
) {}
