package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

public class ModifyListing extends LoggedInCommand{
    @Override
    public void ProtectedExecute(Controller controller) throws PermissionException {
        Request request = controller.getCurrentRequest();
        Listing listing = Listings.GetListing(Long.parseLong(request.params("lid")));
        if(listing != null){
            listing.setDesc(request.queryParams("dis"));
            listing.setRent(request.queryParams("rent"));
            listing.setAddress(request.queryParams("address"));
            listing.setPayment(Listing.getPaymentValue(request.queryParams("payment")));
            listing.setGender(Listing.getGenderValue(request.queryParams("gender")));
            listing.setHousingType(Listing.getHousing_type(request.queryParams("housing")));
            listing.setFurnished(Listing.getIs_furnished(request.queryParams("furn")));
            listing.setUtilIncluded(request.queryParams().contains("utilincluded"));
            listing.setParkingType(Listing.getParkingValue(request.queryParams("parking")));
            Listings.UpdateListing(listing, controller.getSessionUser());
        }else{
            throw new PermissionException("You can't edit this post");
        }
        controller.addRedirect(Path.Web.USER);

    }
}
