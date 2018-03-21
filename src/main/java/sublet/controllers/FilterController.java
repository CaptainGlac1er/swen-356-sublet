package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.Listing;

import java.util.Map;

public class FilterController extends Controller {
    public FilterController(Request request, Response response) {
        super(request, response);
    }
    public Map<String, Object> getModel(){
        model.put("gender", Listing.genderList);
        model.put("housing", Listing.housingList);
        return model;
    }
}
