package sublet.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.Commands.Command;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IndexController extends Controller {
    public IndexController(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void Execute() {
    }

    @Override
    public void Execute(Command command) {

    }

    public ArrayList<Listing> getListings(){
        return Listings.GetListings();
    }
    public Map<String, Object> getModel(){
        model.put("listings",getListings());
        return model;
    }
}
