package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;

import java.util.Map;

public class ListingController extends Controller {

    public ListingController(Request request, Response response) {
        super(request, response);
    }

    public Map<String, Object> getModel(){
        model.put("gender",Listing.genderList);
        model.put("payment",Listing.paymentList);
        model.put("house",Listing.housingList);
        model.put("furn",Listing.furnishedList);
        model.put("parking",Listing.parkingList);
        return model;
    }
}
