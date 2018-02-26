package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.Map;

public class UserController extends Controller {
    public UserController(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void Execute() {

    }

    @Override
    public void Execute(Command command) {
        command.Execute(currentRequest, currentResponse, this);
    }

    public ArrayList<Listing> getListings(){
        return Listings.GetListings(sessionUser);
    }
    public Map<String, Object> getModel(){
        model.put("listings",getListings());
        return model;
    }
}
