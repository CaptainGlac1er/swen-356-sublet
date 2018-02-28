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
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        CurrentUser.logoutUser(Long.parseLong(request.cookie("session")));
        controller.updateUserStatus(new GuestUser());
    }
}
