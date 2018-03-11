package sublet.Commands.User;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Users;

public class LogOutUser extends LoggedInCommand {

    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException {
        Request request = controller.getCurrentRequest();
        System.out.println(request.cookie("session"));
        request.session(false);
        controller.removeSession();
        Users.logoutUser(request.cookie("session"));
        controller.addRedirect("/");
    }
}
