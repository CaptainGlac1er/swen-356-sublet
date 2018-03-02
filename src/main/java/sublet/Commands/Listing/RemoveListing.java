package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.controllers.Controller;
import sublet.models.Listings;
import sublet.util.Path;


public class RemoveListing implements Command {

    @Override
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        try {
            Listings.RemoveListing(Long.parseLong(request.params("lid")),controller.getSessionUser());
        }catch (Exception e){

        }
        controller.addRedirect(Path.Web.USER);
    }
}
