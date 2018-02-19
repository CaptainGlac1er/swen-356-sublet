package sublet.models;

import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrentUser {
    private static Map<Long,StandardUser> currentUsers = new HashMap<>();
    public static StandardUser getCurrentUser(Request request, Response response){
        request.session(true);
        System.out.println(request.session().attribute("session") + " " + request.cookie("session"));
        StandardUser currentUser;
        if(request.session().attribute("session") == null){
            long newSession;
            if(request.cookie("session") != null && currentUsers.containsKey(newSession = Long.parseLong(request.cookie("session")))){
                currentUser = currentUsers.get(newSession);
            }else {
                newSession = (new Random()).nextLong();
                currentUser = new StandardUser(newSession, getRandomString(), getRandomString(), "JD", "asdfghjkl", "fdsjf@fdjf.net", new Date(), new Date());
                request.session().attribute("session", newSession);
                response.cookie("session", Long.toString(newSession),600000);
            }
            currentUsers.put(newSession, currentUser);
        }else{
            currentUser = currentUsers.get(request.session().attribute("session"));
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
