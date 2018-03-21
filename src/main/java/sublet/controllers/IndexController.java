package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;

import java.util.ArrayList;
import java.util.Map;

public class IndexController extends Controller {
    public IndexController(Request request, Response response) {
        super(request, response);
    }


    public ArrayList<Listing> getListings(){
        return Listings.GetActiveListings();
    }

    public Map<String, Object> getModel(){
        model.put("listings",getListings());
        model.put("gender",Listing.genderList);
        model.put("housing", Listing.housingList);
        return model;
    }
}
