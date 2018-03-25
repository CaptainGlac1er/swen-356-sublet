package sublet.Exceptions;

import sublet.models.Logging;

public abstract class BaseException extends Exception {
    private String message;
    private String type;
    private Exception exception;

    BaseException(String message, String type, Exception exception) {
        this.message = message;
        this.type = type;
        this.exception = exception;
        Logging.HandleException(this);
    }

    public String getMessage() {
        return message;
    }

    public Exception getStartingException() {
        return exception;
    }

    public String getTitle() {
        return type;
    }
}
