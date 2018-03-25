package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.NotLoggedInException;
import sublet.Exceptions.PermissionException;
import sublet.models.Roles;
import sublet.models.User;
import sublet.models.Users;
import sublet.util.Path;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public abstract class Controller {
    final Request currentRequest;
    final Response currentResponse;
    User sessionUser;
    Map<String, Object> model = new HashMap<>();

    public Controller(Request request, Response response) {
        currentRequest = request;
        currentResponse = response;
        try {
            updateUserStatus(Users.getCurrentUser(request));
        } catch (DatabaseException exception) {
            this.currentRequest.session().attribute("error", exception);
        }
        addToModel("links", Path.Web.class);
    }

    /**
     * Handles commands that the controller needs to run
     *
     * @param command
     */
    public void Execute(Command command) throws ParseException {
        try {
            if (hasException())
                this.addToModel("error", getLatestException());
            command.Execute(this);
        } catch (NotLoggedInException | PermissionException exception) {
            this.addException(exception);
            this.addRedirect("/");
        } catch (BaseException be) {
            this.addToModel("error", be);
        }
    }

    void addException(Exception exception) {
        this.currentRequest.session().attribute("error", exception);

    }

    private boolean hasException() {
        return this.currentRequest.session().attribute("error") != null;
    }

    private BaseException getLatestException() {
        BaseException exception = this.currentRequest.session().attribute("error");
        this.currentRequest.session().removeAttribute("error");
        return exception;
    }

    /**
     * Updates the user status
     *
     * @param user
     */
    private void updateUserStatus(User user) {
        sessionUser = user;
        model.put("currentuser", sessionUser);
        if (isLoggedIn()) {
            model.put("loggedin", true);
        } else {
            model.put("loggedin", false);
        }

    }

    /***
     * Adds object to model
     * @param name
     * @param object
     */
    public void addToModel(String name, Object object) {
        this.model.put(name, object);
    }

    /**
     * Check if the user is logged in
     *
     * @return returns true if logged in
     */
    public boolean isLoggedIn() {
        return !sessionUser.getUserRoles().contains(Roles.CurrentRoles.get("Guest"));
    }

    /**
     * Get the current user of this controller
     *
     * @return User of the controller
     */
    public User getSessionUser() {
        return this.sessionUser;
    }

    public Request getCurrentRequest() {
        return this.currentRequest;
    }

    public Response getCurrentResponse() {
        return this.currentResponse;
    }

    /**
     * Creates session in controller and tells the client to add a cookie with the session id.
     *
     * @param session session id of client
     */
    public void createSession(String session) {
        try {
            this.updateUserStatus(Users.getCurrentUser(session));
        } catch (DatabaseException exception) {
            this.currentRequest.session().attribute("error", exception);
        }
        this.currentResponse.cookie("/", "session", session, 600000, false);
    }

    /**
     * Removes session from controller and tells the client browser to clear the cookie
     */
    public void removeSession() {
        this.updateUserStatus(Users.newGuest());
        this.currentResponse.removeCookie("/", "session");
        this.currentRequest.session().removeAttribute("session");
    }

    /**
     * Redirects to different page
     *
     * @param url relative path to another page
     */
    public void addRedirect(String url) {
        this.currentResponse.status(302);
        this.currentResponse.header("Location", url);
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
