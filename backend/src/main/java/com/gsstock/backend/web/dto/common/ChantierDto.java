package com.gsstock.backend.web.dto.common;

public record ChantierDto(
        Long id,
        String code,
        String nom,
        boolean active
) {}
