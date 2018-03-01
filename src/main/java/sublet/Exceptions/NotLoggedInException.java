package sublet.Exceptions;

public class NotLoggedInException extends BaseException {
    public NotLoggedInException(String message) {
        super(message, "User Exception");
    }
}
