package sublet.models;

import java.util.Date;
import java.util.Random;

public class CurrentUser {
    private static StandardUser currentUser;
    public static StandardUser getCurrentUser(){
        if(currentUser == null){
            currentUser = new StandardUser(12345,getRandomString(), getRandomString(),"JD","asdfghjkl", "fdsjf@fdjf.net", new Date(), new Date());
            System.out.println(currentUser.getFname());
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
