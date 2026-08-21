package com.naderaria.commoncore.exception;

public final class ForbiddenException extends BusinessException {

    public ForbiddenException(String errorCode) {
        super(errorCode, 403);
    }

    public ForbiddenException() {
        super("Forbidden_exception", 403);
    }

}