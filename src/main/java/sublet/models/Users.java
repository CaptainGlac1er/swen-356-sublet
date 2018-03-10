package sublet.models;

import spark.Request;
import sublet.Exceptions.LoginException;
import sublet.util.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Users {
    private static Map<Long, User> userDataBase = new HashMap<>();
    private static Map<Long,Long> currentUsers = new HashMap<>();

    public static long loginUser(String user, String password) throws LoginException {
        long newSession;
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
        throw new LoginException("Incorrect Login Information");
    }
    public static void logoutUser(long sid){
        currentUsers.remove(sid);
    }

    public static long registerUser(User user) {
        long newSession = (new Random()).nextLong();
        userDataBase.put(user.getUID(),user);
        currentUsers.put(newSession,user.getUID());
        return newSession;
    }
    public static User getCurrentUserUID(long UID){
        return userDataBase.get(UID);
    }
    public static User getCurrentUser(Request request){
        request.session(true);
        if(request.cookie("session") != null && Long.parseLong(request.cookie("session")) == 1){
            return newGuest();
        }
        User currentUser;
        if(request.session().attribute("session") == null){
            long newSession;
            if(request.cookie("session") != null && currentUsers.containsKey(newSession = Long.parseLong(request.cookie("session")))){
                currentUser = userDataBase.get(currentUsers.get(newSession));
            }else {
                return newGuest();
            }
            if(currentUser != null) {
                currentUsers.put(newSession, currentUser.getUID());
            }
        }else{
            currentUser = userDataBase.get(currentUsers.get(request.session().attribute("session")));
        }
        return currentUser;
    }
    public static User getCurrentUser(long sid){
        return getCurrentUserUID(currentUsers.get(sid));
    }

    private static String getRandomString(){
        StringBuilder out = new StringBuilder("");
        for(int i = 0; i < 10; i++)
            out.append((char)((new Random()).nextDouble() * ('Z' - 'A') + 'A'));
        return out.toString();
    }

    public static User newUser(long UID, String fname, String lname, String username, String pass, String email, Date birthday, Date gradYear) {
        User user = new User();
        user.setUID(UID);
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

    public static User newGuest() {
        User user = new User();
        for (String key : Roles.CurrentRoles.keySet()) {
            System.out.println(key + " " + Roles.CurrentRoles.get(key).toString());
        }
        user.getUserRoles().add(Roles.CurrentRoles.get("Guest"));
        return user;
    }

}
