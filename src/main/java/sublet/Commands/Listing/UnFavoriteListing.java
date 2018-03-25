package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

public class UnFavoriteListing extends LoggedInCommand {
    public void ProtectedExecute(Controller controller) throws PermissionException, DatabaseException {
        Request request = controller.getCurrentRequest();
        if (controller.isLoggedIn()) {
            Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")), controller.getSessionUser());
            Listings.RemoveUserFavoriteListing(controller.getSessionUser(), listing);
            controller.addRedirect(Path.Web.USER);
        } else {
            controller.addRedirect(Path.Web.INDEX);
        }
    }
}
