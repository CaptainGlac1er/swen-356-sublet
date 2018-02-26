package sublet.controllers;
import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;

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
                modifyListing();
                break;
            case "edit":
                editListing();
                break;
        }
    }

    private void addListing(){
        if(sessionUser instanceof StandardUser) {
            Listing L = new Listing((new Random()).nextLong(), sessionUser,
                    currentRequest.queryParams("dis"),
                    currentRequest.queryParams("rent"),
                    Listing.PaymentFrequencyOptions.MONTHLY,
                    Listing.GenderOptions.MALE,
                    Listing.HousingTypeOptions.PARKPOINT,
                    Listing.IsFurnishedOptions.FURNISHED);

            Listings.AddListing(L);
        }
    }
    private void modifyListing(){
        Listing listing = Listings.GetListing(Long.parseLong(currentRequest.queryParams("lid")),sessionUser);
        if(listing != null){
            listing.setDesc(currentRequest.queryParams("dis"));
            listing.setRent(currentRequest.queryParams("rent"));
            Listings.UpdateListing(listing, sessionUser);
        }else{
            //oops
        }
    }
    private void editListing(){
        Listing listing = Listings.GetListing(Long.parseLong(currentRequest.queryParams("lid")),sessionUser);
        if(listing != null){
            model.put("listing", listing);
        }
    }
    private void removeListing(){
        try {
            Listings.RemoveListing(Long.parseLong(currentRequest.queryParams("lid")),sessionUser);
        }catch (Exception e){

        }
    }
}
