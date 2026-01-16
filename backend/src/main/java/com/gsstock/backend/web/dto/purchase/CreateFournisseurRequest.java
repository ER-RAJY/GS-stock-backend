package com.gsstock.backend.web.dto.purchase;

import jakarta.validation.constraints.NotBlank;

public record CreateFournisseurRequest(
        @NotBlank String nom,
        String ice,
        String telephone,
        String email,
        String adresse
) {}
