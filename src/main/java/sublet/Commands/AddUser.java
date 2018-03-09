package sublet.Commands;

import spark.Request;
import sublet.Exceptions.RegisterException;
import sublet.controllers.Controller;
import sublet.models.CurrentUser;
import sublet.models.StandardUser;
import sublet.util.Path;
import java.util.ArrayList;

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
                            format.parse(request.queryParams("birthday")),
                            format.parse(request.queryParams("gradyear")));
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
            exceptions.add("Passwords didn't match. ");
        }

        if(!isRITEmail(email)){
            String sentenceStart = exceptions.size()!=0?"Also, y" : "Y";
            exceptions.add(sentenceStart + "ou must use your RIT email.");
        }
        return exceptions;
    }
}