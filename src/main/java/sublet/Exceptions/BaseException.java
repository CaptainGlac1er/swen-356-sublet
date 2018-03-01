package sublet.Exceptions;

public abstract class BaseException extends Exception {
    private String message;
    BaseException(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
