package exception;

public final class DataReferencedException extends BusinessException {

    public DataReferencedException(String errorCode) {
        super(errorCode, 304);

    }

    public DataReferencedException() {
        super("data_referenced_exception", 304);
    }

}