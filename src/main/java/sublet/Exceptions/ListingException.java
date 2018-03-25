package sublet.Exceptions;

public class ListingException extends BaseException {
    public ListingException(String message, Exception e) {
        super(message, "Listing Exception", e);
    }
}
