package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.Listing;
import sublet.models.Roles;

import java.util.ArrayList;
import java.util.Map;

public class FilterController extends IndexController {
    public FilterController(Request request, Response response) {
        super(request, response);
    }

    private ArrayList<Listing> filterHousing(String gender, String housing) {
        ArrayList<Listing> listings = getListings(), ret = new ArrayList<>();
        if (sessionUser.getUserRoles().contains(Roles.CurrentRoles.get("RIT"))) {
            listings.addAll(getRitListings());
        }
        for (Listing l : listings) {
            if ((gender.equals("") || l.getGender().name().equals(gender)) && (housing.equals("") || l.getHousingType().name().equals(housing))) {
                ret.add(l);
            }
        }
        return ret;
    }

    @Override
    public Map<String, Object> getModel() {
        model.put("gender", Listing.genderList);
        model.put("housing", Listing.housingList);
        model.put("listings", filterHousing(currentRequest.queryParams("gender_options"), currentRequest.queryParams("housing_options")));
        return model;
    }
}
