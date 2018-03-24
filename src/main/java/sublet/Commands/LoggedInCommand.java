package sublet.Commands;

import sublet.Exceptions.*;
import sublet.controllers.Controller;
import sublet.models.Roles;

public class LoggedInCommand implements Command {
    private void checkLogin(Controller controller) throws NotLoggedInException {
        if (controller.getSessionUser().getUserRoles().contains(Roles.CurrentRoles.get("Guest")))
            throw new NotLoggedInException();

    }
    public void Execute(Controller controller) throws BaseException {
        checkLogin(controller);
        ProtectedExecute(controller);
    }

    public void ProtectedExecute(Controller controller) throws PermissionException, SubletException, DatabaseException {

    }

}
