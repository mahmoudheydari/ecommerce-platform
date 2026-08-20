package exception;

public final class ValidationException extends BusinessException {

    public ValidationException(String errorCode) {
        super(errorCode, 400);
    }

    public ValidationException() {
        super("Validation_Exception", 400);
    }

}