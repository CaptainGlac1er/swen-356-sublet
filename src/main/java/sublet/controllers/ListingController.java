package sublet.controllers;
import spark.Request;
import spark.Response;
import spark.Route;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingController extends Controller {

    public ListingController(Request request, Response response) {
        super(request, response);
    }

    public void addListing(Listing listing){
        Listings.AddListing(listing);
    }
    public ArrayList<Listing> getListings(){
        return Listings.GetListings();
    }

}
