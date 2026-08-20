package dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorResponse(
        String messageCode,
        List<FieldError> fieldErrors,
        LocalDateTime timestamp
) {
}
