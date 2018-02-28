package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
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
        links.put("LOGOUT", Path.Web.LOGOUT);
        links.put("MODIFYLISTING", Path.Web.MODIFYLISTING);
        links.put("NEWUSER", Path.Web.NEWUSER);
        addToModel("links", links);
    }
    public abstract void Execute();
    public abstract void Execute(Command command);
    public void updateUserStatus(User user){
        sessionUser =  user;
        model.put("currentuser", sessionUser);
        if(sessionUser instanceof GuestUser){
            model.put("loggedin", false);
        }
        if(sessionUser instanceof StandardUser) {
            model.put("loggedin", true);
        }

    }
    public void addToModel(String name, Object object){
        this.model.put(name, object);
    }
    public boolean isLoggedIn(){
        return sessionUser instanceof StandardUser;
    }
    public User getSessionUser(){
        return this.sessionUser;
    }
    public Request getCurrentRequest(){
        return this.currentRequest;
    }
    public Response getCurrentResponse(){
        return this.currentResponse;
    }
    public void addRedirect(String url){
        this.currentResponse.status(302);
        this.currentResponse.header("Location", url);
    }
    public Map<String, Object> getModel(){
        return model;
    }
}
