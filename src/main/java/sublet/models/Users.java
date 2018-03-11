package sublet.models;

import spark.Request;
import sublet.Exceptions.LoginException;
import sublet.util.Security;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Users {
    //private static Map<Long, User> userDataBase = new HashMap<>();
    private static Map<String, Long> currentUsers = new HashMap<>();

    public static String loginUser(String user, String password) throws LoginException {
        String newSession;
        if (CheckLogin(user, Security.getSHA256Hash(password))) {
            User u = GetUser(user);
            newSession = GetRandomSession(u);
            System.out.println(u.toString());
            currentUsers.put(newSession, u.getUID());
            System.out.println(u.getUsername() + " has been logged in");
            return newSession;
        }
        throw new LoginException("Incorrect Login Information");



        /*long newSession;
        User currentUser;
        for (User u :
             userDataBase.values()) {
            System.out.println(u.getUsername() + " " + u.getPassword() + " == " + user + " " + Security.getSHA256Hash(password));
            if (u.getUsername().equals(user) && u.checkPass(password)) {
                    newSession = (new Random()).nextLong();
                    currentUser = u;
                    currentUsers.put(newSession,currentUser.getUID());
                System.out.println(u.getUsername() + " has been logged in");
                    return newSession;
                }
        }
        throw new LoginException("Incorrect Login Information");*/
    }

    public static void logoutUser(String sid) {
        currentUsers.remove(sid);
    }

    public static String registerUser(User user) {
        String newSession = GetRandomSession(user);
        AddUser(user);
        //userDataBase.put(user.getUID(),user);
        currentUsers.put(newSession,user.getUID());
        return newSession;
    }
    public static User getCurrentUserUID(long UID){
        return GetUser(UID);
    }
    public static User getCurrentUser(Request request){
        request.session(true);
        User currentUser;
        System.out.println(request.session().attribute("session") + " " + request.cookie("session"));
        if(request.session().attribute("session") == null){
            String newSession;
            if (request.cookie("session") != null && currentUsers.containsKey(newSession = request.cookie("session"))) {
                currentUser = getCurrentUser(newSession);
                request.session().attribute("session", newSession);
            }else {
                return newGuest();
            }
            if(currentUser != null) {
                currentUsers.put(newSession, currentUser.getUID());
            }
        }else{
            currentUser = getCurrentUserUID(currentUsers.get(request.session().attribute("session")));
        }
        return currentUser;
    }

    public static User getCurrentUser(String sid) {
        return getCurrentUserUID(currentUsers.get(sid));
    }

    public static User newUser(String fname, String lname, String username, String pass, String email, LocalDate birthday, LocalDate gradYear) {
        User user = new User();
        user.setFname(fname);
        user.setLname(lname);
        user.setUsername(username);
        user.setPassword(pass);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setGradYear(gradYear);
        user.getUserRoles().add(Roles.CurrentRoles.get("User"));
        return user;
    }

    private static User newUser(long UID, String fname, String lname, String username, String pass, String email, LocalDate birthday, LocalDate gradYear) {
        User user = newUser(fname, lname, username, pass, email, birthday, gradYear);
        user.setUID(UID);
        return user;
    }

    public static User newGuest() {
        User user = new User();
        for (String key : Roles.CurrentRoles.keySet()) {
            System.out.println(key + " " + Roles.CurrentRoles.get(key).toString());
        }
        user.getUserRoles().add(Roles.CurrentRoles.get("Guest"));
        return user;
    }

    private static void AddUser(User user) {
        try {
            PreparedStatement addUserPS = DatabaseConnection.write.getConnection().prepareStatement("INSERT INTO userdb (fname, lname, username, email, password, birthday, gradYear) VALUES (?, ?, ?, ?, ?, ?, ?)");
            addUserPS.setString(1, user.getFname());
            addUserPS.setString(2, user.getLname());
            addUserPS.setString(3, user.getUsername());
            addUserPS.setString(4, user.getEmail());
            addUserPS.setString(5, user.getPassword());
            addUserPS.setDate(6, java.sql.Date.valueOf(user.getBirthday()));
            addUserPS.setDate(7, java.sql.Date.valueOf(user.getGradYear()));
            user.setUID(addUserPS.executeUpdate());
        } catch (SQLException e) {
            System.out.println(e.toString());

        }

    }

    private static User GetUser(long uid) {
        User user = null;
        try {
            PreparedStatement getUserPS = DatabaseConnection.read.getConnection().prepareStatement("SELECT uid, fname, lname, username, email, password, birthday, gradYear FROM userdb WHERE uid = ?");
            getUserPS.setLong(1, uid);
            ResultSet rs = getUserPS.executeQuery();
            if (rs.next()) {
                user = newUser(rs.getLong("uid"), rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getDate("birthday").toLocalDate(), rs.getDate("gradYear").toLocalDate());
            }
        } catch (SQLException e) {

            System.out.println(e.toString());
        }
        return user;
    }

    private static User GetUser(String username) {
        User user = null;
        try {
            PreparedStatement getUserPS = DatabaseConnection.read.getConnection().prepareStatement("SELECT uid, fname, lname, username, email, password, birthday, gradYear FROM userdb WHERE username = ?");
            getUserPS.setString(1, username);
            ResultSet rs = getUserPS.executeQuery();
            if (rs.next()) {
                user = newUser(rs.getLong("uid"), rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getDate("birthday").toLocalDate(), rs.getDate("gradYear").toLocalDate());
            }
        } catch (SQLException e) {

        }
        return user;
    }

    private static boolean CheckLogin(String user, String password) {
        boolean ret = false;
        try {
            PreparedStatement getUserPS = DatabaseConnection.read.getConnection().prepareStatement("SELECT EXISTS(SELECT 1 FROM userdb WHERE username = ? AND password = ?) AS 'result'");
            getUserPS.setString(1, user);
            getUserPS.setString(2, password);
            ResultSet rs = getUserPS.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("result") == 1;
            }
        } catch (SQLException e) {

        }
        return ret;
    }

    private static String GetRandomSession(User user) {
        return Security.getSHA256Hash(String.format("%d%d", user.hashCode(), (new Random()).nextLong()));
    }
}
