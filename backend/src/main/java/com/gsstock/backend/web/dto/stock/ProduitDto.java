package com.gsstock.backend.web.dto.stock;

import java.math.BigDecimal;

public record ProduitDto(
        Long id,
        String designation,
        String unite,
        BigDecimal stockMin,
        BigDecimal stockActuel,
        boolean active
) {}
