package com.gsstock.backend.web.dto.common;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        List<String> details
) {}
