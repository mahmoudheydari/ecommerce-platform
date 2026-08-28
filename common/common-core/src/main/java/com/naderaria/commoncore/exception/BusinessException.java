package com.naderaria.commoncore.exception;

import com.naderaria.commoncore.dto.response.ErrorResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class BusinessException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    private final String message;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.message = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                errorCode.getCode(),
                LocalDateTime.now(),
                errorCode.getStatus()
        );
    }

    public static BusinessException of(ErrorCode errorCode) {
        return new BusinessException(errorCode);
    }

    public static BusinessException of(ErrorCode errorCode, String message) {
        return new BusinessException(errorCode, message);
    }

    public static Supplier<BusinessException> supplier(ErrorCode errorCode) {
        return () -> of(errorCode);
    }
}