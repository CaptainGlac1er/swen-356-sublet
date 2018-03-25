package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Exceptions.DatabaseException;
import sublet.models.Listing;
import sublet.models.Listings;

import java.util.ArrayList;
import java.util.Map;

public class UserController extends Controller {
    public UserController(Request request, Response response) {
        super(request, response);
    }


    public ArrayList<Listing> getListings() {
        try {
            return Listings.GetUserListings(sessionUser);
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Listing> getActiveListing() {
        try {
            return Listings.GetUserActiveListings(sessionUser);
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();

    }

    private ArrayList<Listing> getArchiveListing() {
        try {
            return Listings.GetUserArchiveListings(sessionUser);
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Listing> getFavoriteListing() {
        try {
            return Listings.GetUserFavoritedListings(sessionUser);
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Listing> getUserRitListings() {
        try {
            return Listings.GetUserRitListings(sessionUser);
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    public Map<String, Object> getModel() {

        ArrayList<Listing> listings = getActiveListing();
        listings.addAll(getUserRitListings());
        model.put("activelistings", listings);
        model.put("archivelistings", getArchiveListing());
        model.put("favoritedlistings", getFavoriteListing());
        return model;
    }
}
