package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.Tag;
import sublet.util.Path;

public class ModifyListing extends LoggedInCommand{
    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")),controller.getSessionUser());
        if(listing != null){
            listing.setDesc(request.queryParams("dis"));
            listing.setRent(request.queryParams("rent"));

            for(Tag p: listing.getTags())
                if(!request.queryParams().contains(p.getName()))
                    listing.removeTag(p.getName());
            if(request.queryParams().contains("utilies"))
                listing.addTag(new Tag("utilies"));
            if(request.queryParams().contains("parking"))
                listing.addTag(new Tag("parking"));

            Listings.UpdateListing(listing, controller.getSessionUser());
        }else{
            throw new PermissionException("You can't edit this post");
        }
        controller.addRedirect(Path.Web.USER);

    }
}
