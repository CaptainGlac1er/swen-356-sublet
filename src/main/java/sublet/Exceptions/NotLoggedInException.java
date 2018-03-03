package sublet.Exceptions;

public class NotLoggedInException extends BaseException {
    public NotLoggedInException() {
        super("Not Logged in, please login again", "User Exception");
    }
}
