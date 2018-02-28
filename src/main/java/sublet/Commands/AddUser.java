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
        System.out.println("A");
        if (!controller.isLoggedIn()) {
            System.out.println("B");
            StandardUser user = new StandardUser(new Random().nextLong(),
                    request.queryParams("fname"),
                    request.queryParams("lname"),
                    request.queryParams("username"),
                    request.queryParams("password"),
                    request.queryParams("email"),
                    new Date(12345), new Date(3456));

            CurrentUser.registerUser(user);
            controller.addRedirect(Path.Web.USER);
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());


        }
    }
}