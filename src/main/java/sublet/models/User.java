package sublet.models;

import sublet.util.Security;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private long UID;
    private String fname;
    private String lname;
    private String username;

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.getSHA256Hash(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getGradYear() {
        return gradYear;
    }

    public void setGradYear(LocalDate gradYear) {
        this.gradYear = gradYear;
    }

    public ArrayList<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(ArrayList<Role> userRoles) {
        this.userRoles = userRoles;
    }


    private String password;
    private String email;
    private LocalDate birthday;
    private LocalDate gradYear;
    private ArrayList<Role> userRoles;

    public boolean checkPass(String pw) {
        return this.password.equals(Security.getSHA256Hash(pw));
    }

    public boolean checkIfSameUser(User user) {
        return user.UID == this.UID;
    }

    public User() {
        userRoles = new ArrayList<>();
    }

}
