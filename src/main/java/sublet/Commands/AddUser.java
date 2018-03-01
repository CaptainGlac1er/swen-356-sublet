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
            if(request.queryParams("password").equals(request.queryParams("confirmpassword"))){
                StandardUser user = new StandardUser(new Random().nextLong(),
                        request.queryParams("fname"),
                        request.queryParams("lname"),
                        request.queryParams("username"),
                        request.queryParams("password"),
                        request.queryParams("email"),
                        new Date(12345), new Date(3456));
                long session = CurrentUser.registerUser(user);
                controller.createSession(session);
                controller.addRedirect(Path.Web.USER);
            }

        }
    }
}