package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.BaseException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;

public class EditListing extends LoggedInCommand {

    @Override
    public void ProtectedExecute(Controller controller) {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")),controller.getSessionUser());
        if(listing != null){
            controller.addToModel("listing", listing);
        }
    }
}
