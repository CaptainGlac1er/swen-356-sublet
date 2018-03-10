package sublet.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class Security {
    public static String getSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return DatatypeConverter.printHexBinary(digest.digest(input.getBytes("UTF-8")));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
