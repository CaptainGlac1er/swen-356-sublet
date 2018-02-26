package sublet.models;
import java.util.Date;

public class StandardUser extends User{
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
    public long getUID() {return this.UID;}
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getEmail(){ return this.email; }
    public Date getBirthday(){ return this.birthday; }
    public Date getGradYear() { return this.gradYear;}
    public boolean checkPass(String UID, String pw){
        return UID.equals(pw);
    }
    public boolean checkIfSameUser(StandardUser user){
        return user.UID == this.UID;
    }

}
