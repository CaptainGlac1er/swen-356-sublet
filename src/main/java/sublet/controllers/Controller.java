package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.CurrentUser;
import sublet.models.GuestUser;
import sublet.models.StandardUser;
import sublet.models.User;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    Map<String, Object> model;
    public Controller(Request request, Response response){
        model = new HashMap<>();
        User user =  CurrentUser.getCurrentUser(request, response);
        model.put("currentuser", user);
        if(user instanceof GuestUser){
            model.put("loggedin", false);
        }
        if(user instanceof StandardUser){
            model.put("loggedin", true);
        }
    }
    public Map<String, Object> getModel(){
        return model;
    }
}
