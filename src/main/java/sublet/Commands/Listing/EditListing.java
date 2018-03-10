package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.PermissionException;
import sublet.Exceptions.SubletException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.Roles;

public class EditListing extends LoggedInCommand {

    @Override
    public void ProtectedExecute(Controller controller) throws SubletException, PermissionException {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")));
        if (listing != null && (controller.getSessionUser().checkIfSameUser(listing.getUser()) || Roles.CanModListings(controller.getSessionUser().getUserRoles()))) {
            controller.addToModel("listing", listing);
        } else {
            if (listing == null)
                throw new SubletException("Listing was not found");
            throw new PermissionException("You don't have access to mod this listing");
        }
    }
}
