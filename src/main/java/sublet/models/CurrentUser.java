package sublet.models;

import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrentUser {
    private static Map<Long,User> userDataBase = new HashMap<>();
    private static Map<Long,Long> currentUsers = new HashMap<>();

    public static void loginUser(String user, String password, Response response){
        long newSession;
        User currentUser;
        for (User u:
             userDataBase.values()) {
                if(u instanceof StandardUser && ((StandardUser)u).checkPass(password)){
                    newSession = (new Random()).nextLong();
                    currentUser = new StandardUser(newSession, getRandomString(), getRandomString(), "JD", "asdfghjkl", "fdsjf@fdjf.net", new Date(), new Date());
                    response.cookie("session", Long.toString(newSession),600000);

                }
        }
    }
    public static void registerUser(StandardUser user){
        userDataBase.put()
    }
    public static User getCurrentUser(Request request, Response response){
        if(request.cookie("session") != null && Long.parseLong(request.cookie("session")) == 1){
            return new GuestUser();
        }
        request.session(true);
        //*System.out.println(request.session().attribute("session") + " " + request.cookie("session"));
        User currentUser;
        if(request.session().attribute("session") == null){
            long newSession;
            if(request.cookie("session") != null && currentUsers.containsKey(newSession = Long.parseLong(request.cookie("session")))){
                currentUser = userDataBase.get(currentUsers.get(newSession));
            }else {
                return new GuestUser();
            }
            currentUsers.put(newSession, currentUser);
        }else{
            currentUser = userDataBase.get(currentUsers.get(request.session().attribute("session")));
        }
        return currentUser;
    }

    private static String getRandomString(){
        StringBuilder out = new StringBuilder("");
        for(int i = 0; i < 10; i++)
            out.append((char)((new Random()).nextDouble() * ('Z' - 'A') + 'A'));
        return out.toString();
    }
}
