package sublet.Commands;

import sublet.Exceptions.BaseException;
import sublet.Exceptions.NotLoggedInException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.GuestUser;

public class LoggedInCommand implements Command {
    void checkLogin(Controller controller) throws NotLoggedInException {
        if(controller.getSessionUser() instanceof GuestUser)
            throw new NotLoggedInException();

    }
    public void Execute(Controller controller) throws BaseException {
        checkLogin(controller);
        ProtectedExecute(controller);
    }
    public void ProtectedExecute(Controller controller) throws PermissionException{

    }

}
