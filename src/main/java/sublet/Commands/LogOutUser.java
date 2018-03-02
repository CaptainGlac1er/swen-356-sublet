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
        System.out.println(request.cookie("session"));
        request.session(false);
        CurrentUser.logoutUser(Long.parseLong(request.cookie("session")));
        controller.removeSession(Long.parseLong(request.cookie("session")));
        controller.addRedirect("/");
    }
}
