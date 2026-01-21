package com.gsstock.backend.service.pdf;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AchatPdfData(
        String reference,
        LocalDate date,
        String fournisseur,
        List<Line> lines,
        BigDecimal totalHT,
        BigDecimal totalTVA,
        BigDecimal totalTTC,

        // Footer légal
        String companyName,
        String ice,
        String address,
        String phone,
        String email
) {
    public record Line(
            String produit,
            BigDecimal quantite,
            BigDecimal prixHT,
            BigDecimal totalHT
    ) {}
}
