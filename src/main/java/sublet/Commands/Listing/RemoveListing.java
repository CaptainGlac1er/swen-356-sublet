package sublet.Commands.Listing;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.util.Path;

import java.util.Random;


public class RemoveListing implements Command {

    @Override
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        try {
            Listings.RemoveListing(Long.parseLong(request.queryParams("lid")),controller.getSessionUser());
        }catch (Exception e){

        }
        controller.addRedirect(Path.Web.USER);
    }
}
