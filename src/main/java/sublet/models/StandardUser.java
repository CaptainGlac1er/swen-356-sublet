package sublet.models;
import java.util.Date;

public class StandardUser extends User{
    private long UID;
    private String fname;
    private String lname;
    private String username;
    private int password;
    private String email;
    private Date birthday;
    private Date gradYear;

    public StandardUser(long UID, String fname, String lname, String username, String pass, String email, Date birthday, Date gradYear){
        this.UID = UID;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = pass.hashCode();
        this.email = email;
        this.birthday = birthday;
        this.gradYear = gradYear;
    }
    public long getUID() {return this.UID;}
    public String getFname(){
        return this.fname;
    }

    public boolean checkPass(String pw) {
        return this.password == pw.hashCode();
    }
    public String getLname(){
        return this.lname;
    }
    public String getUsername() { return this.username; }
    public long getPassword() { return this.password; }
    public String getEmail(){ return this.email; }
    public Date getBirthday(){ return this.birthday; }
    public Date getGradYear() { return this.gradYear;}
    public boolean checkIfSameUser(StandardUser user){
        return user.UID == this.UID;
    }

}
