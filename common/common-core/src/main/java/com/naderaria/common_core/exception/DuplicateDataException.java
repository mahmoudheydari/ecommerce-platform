package com.naderaria.common_core.exception;

public final class DuplicateDataException extends BusinessException {

    public DuplicateDataException(String errorCode) {
        super(errorCode, 400);

    }

    public DuplicateDataException() {
        super("duplicate_data_exception", 400);
    }

}