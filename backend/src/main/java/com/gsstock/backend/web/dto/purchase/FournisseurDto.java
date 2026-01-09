package com.gsstock.backend.web.dto.purchase;

public record FournisseurDto(
        Long id,
        String nom,
        String ice,
        String phone,
        String address,
        boolean active
) {}
