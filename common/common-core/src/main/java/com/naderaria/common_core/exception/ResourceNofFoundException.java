package com.naderaria.common_core.exception;

public final class ResourceNofFoundException extends BusinessException {

    public ResourceNofFoundException(String errorCode) {
        super(errorCode, 404);
    }

    public ResourceNofFoundException() {
        super("data_not_found", 404);
    }
}