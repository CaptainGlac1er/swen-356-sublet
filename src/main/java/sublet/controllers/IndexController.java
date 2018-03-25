package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Exceptions.DatabaseException;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.Roles;

import java.util.ArrayList;
import java.util.Map;

public class IndexController extends Controller {
    public IndexController(Request request, Response response) {
        super(request, response);
    }


    public ArrayList<Listing> getListings() {
        try {
            return Listings.GetActiveListings();
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    private ArrayList<Listing> getRitListings() {
        try {
            return Listings.GetRitListings();
        } catch (DatabaseException e) {
            this.addException(e);
        }
        return new ArrayList<>();
    }

    public Map<String, Object> getModel(){
        ArrayList<Listing> listings = getListings();
        if (sessionUser.getUserRoles().contains(Roles.CurrentRoles.get("RIT"))) {
            listings.addAll(getRitListings());
        }
        model.put("listings", listings);
        model.put("gender",Listing.genderList);
        model.put("housing", Listing.housingList);
        return model;
    }
}
