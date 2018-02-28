package sublet.Commands;

import spark.Request;
import spark.Response;
import sublet.Exceptions.LoginException;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.User;
import sublet.util.Path;

public class LogInUser implements Command {
    @Override
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        Response response = controller.getCurrentResponse();
        try {
            long session = CurrentUser.loginUser(request.queryParams("username"), request.queryParams("password"));
            User sessionUser = CurrentUser.getCurrentUser(session);
            controller.updateUserStatus(sessionUser);
            response.cookie("/", "session", Long.toString(session), 600000, false);
            controller.addRedirect(Path.Web.USER);
        }catch (LoginException le){
            controller.addToModel("error", le.getMessage());
        }
    }
}
