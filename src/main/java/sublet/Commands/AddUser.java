package sublet.Commands;

import spark.Request;
import sublet.Exceptions.RegisterException;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.StandardUser;
import sublet.util.Path;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AddUser implements Command {

    @Override
    public void Execute(Controller controller) throws RegisterException, ParseException {
        Request request = controller.getCurrentRequest();
        if (!controller.isLoggedIn()) {
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            if(request.queryParams("password").equals(request.queryParams("confirmpassword"))){
                if(isRITEmail(request.queryParams("email"))) {
                    StandardUser user = new StandardUser(new Random().nextLong(),
                            request.queryParams("fname"),
                            request.queryParams("lname"),
                            request.queryParams("username"),
                            request.queryParams("password"),
                            request.queryParams("email"),
                            format.parse(request.queryParams("birthday")),
                            format.parse(request.queryParams("gradyear")));
                    long session = CurrentUser.registerUser(user);
                    controller.createSession(session);
                    controller.addRedirect(Path.Web.USER);
                }else{
                    throw new RegisterException("You must use your RIT email");
                }
            }else{
                throw new RegisterException("Passwords didn't match");
            }

        }
    }

    public boolean isRITEmail(String email){
        String regex = "\\w+@(g\\.|mail\\.)?rit\\.edu";
        return email.matches(regex);
    }
}