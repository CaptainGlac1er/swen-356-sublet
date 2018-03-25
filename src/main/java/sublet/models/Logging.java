package sublet.models;

import sublet.Exceptions.BaseException;

public class Logging {
    public static void HandleException(BaseException e) {
        String exceptionMessage = e.getMessage();
        String exceptionBase = "";
        if (e.getStartingException() != null)
            exceptionBase = e.getStartingException().getMessage();
        System.out.printf("Exception: %s\r\nMore Info: %s\r\n", exceptionMessage, exceptionBase);
    }
}
