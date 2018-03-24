package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

public class SetVisibilityListing extends LoggedInCommand {
    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException, DatabaseException {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")));
        listing.setListingVisibility(Listing.getVisibilityValue(request.queryParams("visibility")));
        Listings.UpdateListingVisibility(listing, controller.getSessionUser());
        controller.addRedirect(Path.Web.USER);
    }
}
