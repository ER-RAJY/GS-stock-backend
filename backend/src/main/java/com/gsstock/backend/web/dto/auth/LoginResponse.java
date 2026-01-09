package com.gsstock.backend.web.dto.auth;

import java.util.Set;

public record LoginResponse(
        String token,
        String username,
        Set<String> roles
) {}
