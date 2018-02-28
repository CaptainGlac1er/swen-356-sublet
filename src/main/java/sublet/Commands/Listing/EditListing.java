package sublet.Commands.Listing;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

public class EditListing implements Command {
    @Override
    public void Execute(Controller controller) throws BaseException {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.queryParams("lid")),controller.getSessionUser());
        if(listing != null){
            controller.addToModel("listing", listing);
        }
    }
}
