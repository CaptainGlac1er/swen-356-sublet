package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;

public class FilterListing implements Command {


    public void Execute(Controller controller) throws BaseException {
        Request request = controller.getCurrentRequest();
        String gender = request.queryParams("gender_options");                      //Sets the gender query param
        String housing = request.queryParams("housing_options");            //Sets the housing query param
        controller.addToModel("gender_options", gender);
        controller.addToModel("housing_options", housing);
    }
}