package sublet.Exceptions;

public abstract class BaseException extends Exception {
    private String message;
    private String type;
    BaseException(String message, String type){
        this.message = message;
        this.type = type;
    }
    public String getMessage() {
        return String.format("%s\r\n%s","Login Exception", message);
    }
}
