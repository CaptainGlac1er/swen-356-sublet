package sublet.Commands;

import spark.Request;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Users;

public class LogOutUser extends LoggedInCommand {

    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException {
        Request request = controller.getCurrentRequest();
        System.out.println(request.cookie("session"));
        request.session(false);
        Users.logoutUser(Long.parseLong(request.cookie("session")));
        controller.removeSession(Long.parseLong(request.cookie("session")));
        controller.addRedirect("/");
    }
}
