package com.naderaria.common_core.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String errorCode,
        LocalDateTime timestamp,
        int status
) {
}
