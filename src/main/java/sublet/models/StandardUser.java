package sublet.models;
import java.util.Date;

public class StandardUser {
    private long UID;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private Date gradYear;

    public StandardUser(long UID, String fname, String lname, String username, String pass, String email, Date birthday, Date gradYear){
        this.UID = UID;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = pass;
        this.email = email;
        this.birthday = birthday;
        this.gradYear = gradYear;
    }

    public boolean checkPass(String UID, String pw){
        return UID.equals(pw);
    }

}
