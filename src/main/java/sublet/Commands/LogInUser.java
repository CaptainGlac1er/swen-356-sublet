package sublet.Commands;

import spark.Request;
import spark.Response;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.StandardUser;
import sublet.models.User;
import sublet.util.Path;

public class LogInUser implements Command {
    @Override
    public void Execute(Request request, Response response, Controller controller) {
        try {
            long session = CurrentUser.loginUser(request.queryParams("username"), request.queryParams("password"));
            User sessionUser = CurrentUser.getCurrentUser(session);
            controller.updateUserStatus(sessionUser);
            response.cookie("/", "session", Long.toString(session), 600000, false);
            response.header("Location", Path.Web.USER);
            response.status(302);
        }catch (NullPointerException e){

        }
    }
}
