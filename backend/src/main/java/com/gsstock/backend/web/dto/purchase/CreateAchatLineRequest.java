package com.gsstock.backend.web.dto.purchase;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;

public record CreateAchatLineRequest(

        @NotNull
        Long produitId,

        @NotNull
        @Positive
        BigDecimal quantite,

        @NotNull
        @Positive
        BigDecimal prixUnitaireHT
) {}
