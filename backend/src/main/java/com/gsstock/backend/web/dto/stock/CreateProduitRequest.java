package com.gsstock.backend.web.dto.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProduitRequest(

        @NotBlank
        String designation,

        @NotNull
        String unite,

        BigDecimal stockMin
) {}
