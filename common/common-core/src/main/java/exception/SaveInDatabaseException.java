package exception;

public final class SaveInDatabaseException extends BusinessException {

    public SaveInDatabaseException(String errorCode) {
        super(errorCode, 500);
    }

    public SaveInDatabaseException() {
        super("save_in_database_exception", 500);
    }

}