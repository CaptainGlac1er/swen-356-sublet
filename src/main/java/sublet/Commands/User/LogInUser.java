package sublet.Commands.User;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.LoginException;
import sublet.controllers.Controller;
import sublet.models.Users;
import sublet.util.Path;

public class LogInUser implements Command {
    @Override
    public void Execute(Controller controller) throws LoginException, DatabaseException {
        Request request = controller.getCurrentRequest();
        Response response = controller.getCurrentResponse();
        String session = Users.loginUser(request.queryParams("username"), request.queryParams("password"));
        controller.createSession(session);
        controller.addRedirect(Path.Web.USER);
    }

}


