package sublet.controllers;
import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;

public class ListingController extends Controller {

    public ListingController(Request request, Response response) {
        super(request, response);
    }

    public void addListing(){

        Listing L = new Listing(sessionUser,
                                currentRequest.queryParams("dis"),
                                currentRequest.queryParams("rent"),
                                Listing.PaymentFrequencyOptions.MONTHLY,
                                Listing.GenderOptions.MALE,
                                Listing.HousingTypeOptions.PARKPOINT,
                                Listing.IsFurnishedOptions.FURNISHED);

        Listings.AddListing(L);
    }

}
