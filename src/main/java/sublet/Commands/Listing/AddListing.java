package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

import java.util.Random;

public class AddListing implements Command {

    @Override
    public void Execute(Controller controller) {
        Request request = controller.getCurrentRequest();
        if(controller.isLoggedIn()) {
            Listing L = new Listing((new Random()).nextLong(), controller.getSessionUser(),
                    request.queryParams("dis"),
                    request.queryParams("rent"),
                    request.queryParams("address"),
                    Listing.getPaymentValue(request.queryParams("payment")),
                    Listing.getGenderValue(request.queryParams("gender")),
                    Listing.getHousing_type(request.queryParams("housing")),
                    Listing.getIs_furnished(request.queryParams("furn")));

            Listings.AddListing(L);
            controller.addRedirect(Path.Web.USER);
        }
    }
}
