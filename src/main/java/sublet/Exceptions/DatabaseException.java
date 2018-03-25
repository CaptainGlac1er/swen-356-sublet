package sublet.Exceptions;

public class DatabaseException extends BaseException {

    public DatabaseException(String message, Exception e) {
        super(message, "Database Exception", e);
    }
}
