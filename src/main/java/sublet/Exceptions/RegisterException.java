package sublet.Exceptions;

public class RegisterException extends BaseException {
    public RegisterException(String message) {
        super(message, "Registration Exception", null);
    }
}
