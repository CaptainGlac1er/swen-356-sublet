package sublet.Commands;

import spark.Request;
import spark.Response;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.GuestUser;

public class LogOutUser implements Command {
    public LogOutUser(){
    }
    @Override
    public void Execute(Request request, Response response, Controller controller) {
        CurrentUser.logoutUser(Long.parseLong(request.cookie("session")));
        controller.updateUserStatus(new GuestUser());
    }
}
