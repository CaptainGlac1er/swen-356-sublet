package sublet.Commands.User;

import spark.Request;
import sublet.Commands.Command;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.RegisterException;
import sublet.controllers.Controller;
import sublet.models.User;
import sublet.models.Users;
import sublet.util.Path;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AddUser implements Command {

    @Override
    public void Execute(Controller controller) throws RegisterException, DatabaseException {
        Request request = controller.getCurrentRequest();
        if (!controller.isLoggedIn()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
            //DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            ArrayList<String> exceptions = isValidFormInputs(request.queryParams("password"),
                    request.queryParams("confirmpassword"),
                    request.queryParams("email"),
                    request.queryParams("fname"),
                    request.queryParams("lname"),
                    request.queryParams("birthday"),
                    request.queryParams("gradyear")
            );

            if(exceptions.size() == 0){
                User user = Users.newUser(request.queryParams("fname"),
                        request.queryParams("lname"),
                        request.queryParams("username"),
                        request.queryParams("password"),
                        request.queryParams("email"),
                        LocalDate.parse(request.queryParams("birthday"), dtf),
                        LocalDate.parse(request.queryParams("gradyear"), dtf));
                String session = Users.registerUser(user);
                    controller.createSession(session);
                    controller.addRedirect(Path.Web.USER);
            }else{
                StringBuilder exceptionString = new StringBuilder("");
               for(String re : exceptions){
                   exceptionString.append(re + "<br />");
               }
                throw new RegisterException(exceptionString.toString());
            }

        }
    }

    private boolean isRITEmail(String email) {
        String regex = "\\w+@(g\\.|mail\\.)?rit\\.edu";
        return email.matches(regex);
    }

    private ArrayList<String> isValidFormInputs(String password, String confirmPassword, String email,
                                                String firstName, String lastName, String birthdayString,
                                                String gradYearString) {
        ArrayList<String> exceptions = new ArrayList<>();
        if(firstName.trim().equals("") | lastName.trim().equals("") | email.trim().equals("") |
           email.trim().equals("") | password.trim().equals("") | confirmPassword.trim().equals("")){
            exceptions.add("Required fields must be filled.");
            return exceptions;
        }

        if(!password.equals(confirmPassword)){
            exceptions.add("Passwords didn't match. ");
        }

        if(!isRITEmail(email)){
            String sentenceStart = exceptions.size()!=0?"Also, y" : "Y";
            exceptions.add(sentenceStart + "ou must use your RIT email.");
        }
        try {
            if (birthdayString.length() > 0)
                LocalDate.parse(birthdayString);
        } catch (DateTimeException dte) {
            exceptions.add("Incorrect birthday");
        }
        try {
            if (gradYearString.length() > 0)
                LocalDate.parse(gradYearString);
        } catch (DateTimeException dte) {
            exceptions.add("Incorrect graduation year");
        }

        return exceptions;
    }
}