package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

import java.util.Random;

public class AddListing extends LoggedInCommand{


    @Override
    public void ProtectedExecute(Controller controller) {
        Request request = controller.getCurrentRequest();
        if(controller.isLoggedIn()) {
            for (String s:request.queryParams()) {
                System.out.println(s + " " + request.queryParams(s));
            }
            Listing L = new Listing((new Random()).nextLong(), controller.getSessionUser(),
                    request.queryParams("dis"),
                    request.queryParams("rent"),
                    request.queryParams("address"),
                    Listing.getPaymentValue(request.queryParams("payment")),
                    Listing.getGenderValue(request.queryParams("gender")),
                    Listing.getHousing_type(request.queryParams("housing")),
                    Listing.getIs_furnished(request.queryParams("furn")),
                    Listing.ParkingOption.ON_STR, request.queryParams().contains("utilincluded"));

            Listings.AddListing(L);
            controller.addRedirect(Path.Web.LISTING + "/" + L.getLID());
        }
    }
}
