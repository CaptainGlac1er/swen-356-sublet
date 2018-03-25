package sublet.Exceptions;

public class PermissionException extends BaseException {
    public PermissionException(String message) {
        super(message, "Permission Exception", null);
    }
}
