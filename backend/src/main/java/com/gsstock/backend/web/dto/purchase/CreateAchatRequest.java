package com.gsstock.backend.web.dto.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateAchatRequest(

        String referenceFacture,

        @NotNull
        LocalDate date,

        @NotNull
        @Positive
        BigDecimal tvaRate,

        @NotNull
        Long fournisseurId
) {}
