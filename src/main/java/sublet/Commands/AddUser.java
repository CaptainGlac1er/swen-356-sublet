package sublet.Commands;

import spark.Request;
import sublet.Exceptions.RegisterException;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.StandardUser;
import sublet.util.Path;
import java.util.ArrayList;

import java.util.Date;
import java.util.Random;

public class AddUser implements Command {

    @Override
    public void Execute(Controller controller) throws RegisterException {
        Request request = controller.getCurrentRequest();
        if (!controller.isLoggedIn()) {
            ArrayList<String> exceptions = isValidFormInputs(request.queryParams("password"),
                    request.queryParams("confirmpassword"),
                    request.queryParams("email"));

            if(exceptions.size() == 0){
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
            }else{
                String exceptionString = "";
               for(String re : exceptions){
                   exceptionString += re;
               }
               throw new RegisterException(exceptionString);
            }

        }
    }

    public boolean isRITEmail(String email){
        String regex = "\\w+@(g\\.|mail\\.)?rit\\.edu";
        return email.matches(regex);
    }

    public ArrayList<String> isValidFormInputs(String password, String confirmPassword, String email){
        ArrayList<String> exceptions = new ArrayList<>();
        if(!password.equals(confirmPassword)){
            exceptions.add("Passwords didn't match.");
        }

        if(!isRITEmail(email)){
            exceptions.add("You must use your RIT email.");
        }
        return exceptions;
    }
}