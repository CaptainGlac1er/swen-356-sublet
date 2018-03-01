package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
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

    /**
     * Handles custom things (probably shouldn't ever be used)
     */
    public abstract void Execute();

    /**
     * Handles commands that the controller needs to run
     * @param command
     */
    public void Execute(Command command){
        try {
            command.Execute(this);
        }catch (BaseException be){
            this.addToModel("error", be.getMessage());
        }
    }

    //TODO Improve this
    /**
     * Updates the user status
     * @param user
     */
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

    /***
     * Adds object to model
     * @param name
     * @param object
     */
    public void addToModel(String name, Object object){
        this.model.put(name, object);
    }

    /**
     * Check if the user is logged in
     * @return returns true if logged in
     */
    public boolean isLoggedIn(){
        return sessionUser instanceof StandardUser;
    }

    /**
     * Get the current user of this controller
     * @return User of the controller
     */
    public User getSessionUser(){
        return this.sessionUser;
    }
    public Request getCurrentRequest(){
        return this.currentRequest;
    }
    public Response getCurrentResponse(){
        return this.currentResponse;
    }

    /**
     * Creates session in controller and tells the client to add a cookie with the session id.
     * @param session session id of client
     */
    public void createSession(long session){
        this.updateUserStatus(CurrentUser.getCurrentUser(session));
        this.currentResponse.cookie("/", "session", Long.toString(session), 600000, false);
    }

    /**
     * Removes session from controller and tells the client browser to clear the cookie
     * @param session session id of client
     */
    public void removeSession(long session){
        this.updateUserStatus(new GuestUser());
        this.currentResponse.removeCookie("session");
    }

    /**
     * Redirects to different page
     * @param url relative path to another page
     */
    public void addRedirect(String url){
        this.currentResponse.status(302);
        this.currentResponse.header("Location", url);
    }
    public Map<String, Object> getModel(){
        return model;
    }
}
