package sublet.Exceptions;

public class BaseException extends Exception {
    private String message;
    BaseException(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
