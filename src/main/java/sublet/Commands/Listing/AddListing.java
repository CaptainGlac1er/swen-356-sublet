package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.LoggedInCommand;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.ListingException;
import sublet.controllers.Controller;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

import java.util.ArrayList;

public class AddListing extends LoggedInCommand{


    @Override
    public void ProtectedExecute(Controller controller) throws DatabaseException, ListingException {
        Request request = controller.getCurrentRequest();
        if(controller.isLoggedIn()) {
            for (String s:request.queryParams()) {
                System.out.println(s + " " + request.queryParams(s));
            }

            ArrayList<String> exceptions = isValidFormInputs(request.queryParams("dis"), request.queryParams("address"),
                    request.queryParams("rent"));

            if (exceptions.size() == 0) {
                Listing L = new Listing(controller.getSessionUser(),
                        request.queryParams("dis"),
                        request.queryParams("rent"),
                        request.queryParams("address"),
                        Listing.getPaymentValue(request.queryParams("payment")),
                        Listing.getGenderValue(request.queryParams("gender")),
                        Listing.getHousing_type(request.queryParams("housing")),
                        Listing.getIs_furnished(request.queryParams("furn")),
                        Listing.getParkingValue(request.queryParams("parking")), request.queryParams().contains("utilincluded"));

                Listings.AddListing(L);
                controller.addRedirect(Path.Web.LISTING + "/" + L.getLID());
            } else {
                StringBuilder exceptionString = new StringBuilder("");
                for (String re : exceptions) {
                    exceptionString.append(re);
                }
                throw new ListingException(exceptionString.toString(), null);
            }
        }
    }

    private ArrayList<String> isValidFormInputs(String description, String address, String rent) {
        ArrayList<String> exceptions = new ArrayList<>();

        if (description.trim().equals("") | address.trim().equals("") | rent.trim().equals(""))
            exceptions.add("Required fields must be filled.");

        return exceptions;
    }
}
