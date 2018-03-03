package sublet.Exceptions;
import org.apache.commons.lang.StringUtils;
public abstract class BaseException extends Exception {
    private String message;
    private String type;
    BaseException(String message, String type){
        this.message = message;
        this.type = type;
    }
    public String getMessage() {
        return message;
    }
    public String getTitle(){
        return type;
    }
}
