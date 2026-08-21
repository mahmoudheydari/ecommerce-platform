package com.naderaria.commoncore.exception;

import com.naderaria.commoncore.dto.response.ErrorResponse;

import java.time.LocalDateTime;

public abstract sealed class BusinessException extends RuntimeException
        permits DataReferencedException, DuplicateDataException, ForbiddenException, ResourceNofFoundException,
        SaveInDatabaseException, ValidationException {

    protected String errorCode;

    protected int status = 500;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorCode, int status) {
        super(errorCode);
        this.status = status;
        this.errorCode = errorCode;
    }

    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(
                this.errorCode,
                LocalDateTime.now(),
                this.status
        );
    }

}