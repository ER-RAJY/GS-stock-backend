package com.gsstock.backend.web.dto.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProduitRequest(

        @NotBlank
        String designation,

        @PositiveOrZero
        BigDecimal stockMin,

        Boolean active
) {}
