package sublet.controllers;
import spark.Request;
import spark.Response;
import spark.Route;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingController extends Controller {
    public ArrayList<Listing> Listings = new ArrayList<>();

    public ListingController(Request request, Response response) {
        super(request, response);
    }

    public void addListing(Listing listing){
        Listings.add(listing);
    }

}
