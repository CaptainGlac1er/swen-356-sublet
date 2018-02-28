package sublet.models;

import spark.Request;
import spark.Response;
import sublet.Exceptions.LoginException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrentUser {
    private static Map<Long,StandardUser> userDataBase = new HashMap<>();
    private static Map<Long,Long> currentUsers = new HashMap<>();

    public static long loginUser(String user, String password) throws LoginException {
        long newSession;
        StandardUser currentUser;
        for (StandardUser u:
             userDataBase.values()) {
                System.out.println(u.getUsername() + " " + u.getPassword() + " == " + user + " " + password.hashCode());
                if(u.getUsername().equals(user) &&u.checkPass(password)){
                    newSession = (new Random()).nextLong();
                    currentUser = u;
                    currentUsers.put(newSession,currentUser.getUID());
                    return newSession;
                }
        }
        throw new LoginException("Incorrect Login Information");
    }
    public static void logoutUser(long sid){
        currentUsers.remove(sid);
    }
    public static long registerUser(StandardUser user){
        long newSession = (new Random()).nextLong();
        userDataBase.put(user.getUID(),user);
        currentUsers.put(newSession,user.getUID());
        return newSession;
    }
    public static User getCurrentUser(Request request){
        //System.out.println(userDataBase.toString());
        //System.out.println(currentUsers.toString());
        request.session(true);
        //System.out.println(request.session().attribute("session") + " " + request.cookie("session"));
        if(request.cookie("session") != null && Long.parseLong(request.cookie("session")) == 1){
            return new GuestUser();
        }
        User currentUser;
        if(request.session().attribute("session") == null){
            long newSession;
            if(request.cookie("session") != null && currentUsers.containsKey(newSession = Long.parseLong(request.cookie("session")))){
                currentUser = userDataBase.get(currentUsers.get(newSession));
            }else {
                return new GuestUser();
            }
            if(currentUser != null) {
                currentUsers.put(newSession, ((StandardUser)currentUser).getUID());
            }
        }else{
            currentUser = userDataBase.get(currentUsers.get(request.session().attribute("session")));
        }
        return currentUser;
    }
    public static User getCurrentUser(long sid){
        return userDataBase.get(currentUsers.get(sid));
    }

    private static String getRandomString(){
        StringBuilder out = new StringBuilder("");
        for(int i = 0; i < 10; i++)
            out.append((char)((new Random()).nextDouble() * ('Z' - 'A') + 'A'));
        return out.toString();
    }
}
