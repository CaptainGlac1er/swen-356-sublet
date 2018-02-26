package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.CurrentUser;
import sublet.models.GuestUser;
import sublet.models.StandardUser;
import sublet.models.User;
import sublet.util.Path;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller {
    User sessionUser;
    Request currentRequest;
    Response currentResponse;
    Map<String, Object> model = new HashMap<>();
    public Controller(Request request, Response response){
        currentRequest = request;
        currentResponse = response;
        updateUserStatus(CurrentUser.getCurrentUser(request));
        HashMap<String, String> links = new HashMap<>();
        links.put("NEWLISTING",Path.Web.NEWLISTING);
        links.put("USER",Path.Web.USER);
        links.put("INDEX",Path.Web.INDEX);
        links.put("ABOUT",Path.Web.ABOUT);
        links.put("LISTING",Path.Web.LISTING);
        links.put("LOGIN", Path.Web.LOGIN);
        model.put("links", links);
    }
    public abstract void Execute();
    protected void updateUserStatus(User user){
        sessionUser =  user;
        model.put("currentuser", sessionUser);
        if(sessionUser instanceof GuestUser){
            model.put("loggedin", false);
        }
        if(sessionUser instanceof StandardUser) {
            model.put("loggedin", true);
        }

    }
    public Map<String, Object> getModel(){
        return model;
    }
}
