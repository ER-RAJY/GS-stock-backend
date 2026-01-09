package com.gsstock.backend.web.dto.stock;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record CreateMovementRequest(

        @NotNull
        Long produitId,

        @NotNull

        BigDecimal quantite,

        String commentaire
) {}
