package com.gsstock.backend.web.dto.auth;


import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

public record LoginRequest(

        @NotBlank
        String username,

        @NotBlank
        String password
) {}
