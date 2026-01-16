package com.gsstock.backend.web.dto.purchase;

import jakarta.validation.constraints.NotBlank;

public record UpdateFournisseurRequest(
        @NotBlank String nom,
        String ice,
        String telephone,
        String email,
        String adresse,
        Boolean active
) {}
