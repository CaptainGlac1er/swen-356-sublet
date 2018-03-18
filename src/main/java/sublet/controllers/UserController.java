package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Listings;

import java.util.ArrayList;
import java.util.Map;

public class UserController extends Controller {
    public UserController(Request request, Response response) {
        super(request, response);
    }



    public ArrayList<Listing> getListings(){
        return Listings.GetUserListings(sessionUser);
    }

    private ArrayList<Listing> getActiveListing() {
        return Listings.GetUserActiveListings(sessionUser);

    }

    private ArrayList<Listing> getArchiveListing() {
        return Listings.GetUserArchiveListings(sessionUser);

    }

    private ArrayList<Listing> getFavoriteListing() {
        return Listings.GetUserFavoritedListings(sessionUser);
    }
    public Map<String, Object> getModel(){
        model.put("activelistings", getActiveListing());
        model.put("archivelistings", getArchiveListing());
        model.put("favoritedlistings", getFavoriteListing());
        return model;
    }
}
