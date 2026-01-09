package com.gsstock.backend.web.dto.auth;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateUserRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotEmpty
        Set<String> roles
) {}
