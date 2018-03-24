package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listings;
import sublet.util.Path;


public class RemoveListing extends LoggedInCommand{


    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException, DatabaseException {
        Request request = controller.getCurrentRequest();
        Listings.RemoveListing(Long.parseLong(request.params("lid")),controller.getSessionUser());
        controller.addRedirect(Path.Web.USER);

    }
}
