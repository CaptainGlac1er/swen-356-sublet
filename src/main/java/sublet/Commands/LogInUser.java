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
            controller.createSession(session);
            controller.addRedirect(Path.Web.USER);
        }catch (LoginException le){
            controller.addToModel("error", le.getMessage());
        }
    }
}
