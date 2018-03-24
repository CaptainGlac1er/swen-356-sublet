package sublet.Exceptions;

public class DatabaseException extends BaseException {

    public DatabaseException(String message) {
        super(message, "Database Exception");
    }
}
