package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;
import sublet.models.Listings;

public class FilterListing implements Command {
    // well, all my changes are gone. lol...
    public void Execute(Controller controller) throws BaseException {
        Request request = controller.getCurrentRequest();
        controller.addToModel("listings", Listings.FilterListing(request));

    }
}