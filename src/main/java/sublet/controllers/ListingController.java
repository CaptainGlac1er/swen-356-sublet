package sublet.controllers;
import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;

import java.util.Random;

public class ListingController extends Controller {

    public ListingController(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void Execute() {
        switch (currentRequest.queryParams("submit")){
            case "add":
                addListing();
                break;
            case "remove":
                removeListing();
                break;
            case "modify":
                break;
        }
    }

    public void addListing(){

        Listing L = new Listing((new Random()).nextLong(),sessionUser,
                                currentRequest.queryParams("dis"),
                                currentRequest.queryParams("rent"),
                                Listing.PaymentFrequencyOptions.MONTHLY,
                                Listing.GenderOptions.MALE,
                                Listing.HousingTypeOptions.PARKPOINT,
                                Listing.IsFurnishedOptions.FURNISHED);

        Listings.AddListing(L);
    }
    public void removeListing(){
        try {
            Listings.RemoveListing(Long.parseLong(currentRequest.queryParams("lid")));
        }catch (Exception e){

        }
    }
}
