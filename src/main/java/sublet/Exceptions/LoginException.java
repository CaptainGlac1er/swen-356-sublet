package sublet.Exceptions;

public class LoginException extends BaseException {

    public LoginException(String message) {
        super(message, "Login Exception", null);
    }

}
