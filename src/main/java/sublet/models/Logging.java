package sublet.models;

import sublet.Exceptions.BaseException;

public class Logging {
    public static void HandleException(Exception e) {
        String exceptionMessage = e.getMessage();
        String exceptionBase = "";
        if (e instanceof BaseException) {
            BaseException be = (BaseException) e;
            if (be.getStartingException() != null)
                exceptionBase = be.getStartingException().getMessage();
        }
        System.out.printf("Exception: %s\r\nMore Info: %s\r\n", exceptionMessage, exceptionBase);
    }
}
