package sublet.Commands;

import spark.Request;
import sublet.controllers.Controller;
import sublet.models.*;
import sublet.util.Path;
import java.util.Random;
import java.util.Date;

public class AddUser implements Command {

    @Override
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        if (!controller.isLoggedIn()) {
            StandardUser user = new StandardUser(new Random().nextLong(),
                    request.queryParams("fname"),
                    request.queryParams("lname"),
                    request.queryParams("username"),
                    request.queryParams("email"),
                    request.queryParams("password"),
                    new Date(12345), new Date(3456));
            controller.addRedirect(Path.Web.USER);
            CurrentUser.registerUser(user);
        }
    }
}