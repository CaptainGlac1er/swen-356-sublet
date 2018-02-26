package sublet.controllers;

import spark.Request;
import spark.Response;
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
        if(currentRequest != null) {
            switch ((currentRequest.queryParams().contains("submit")) ? currentRequest.queryParams("submit") : "") {
                case "login":
                    try {
                        long session = CurrentUser.loginUser(currentRequest.queryParams("username"), currentRequest.queryParams("password"));
                        sessionUser = CurrentUser.getCurrentUser(session);
                        updateUserStatus(sessionUser);
                        currentResponse.cookie("session", Long.toString(session), 600000);
                        currentResponse.header("Location", Path.Web.USER);
                        currentResponse.status(302);
                    }catch (NullPointerException ne){
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public ArrayList<Listing> getListings(){
        return Listings.GetListings(sessionUser);
    }
    public Map<String, Object> getModel(){
        model.put("listings",getListings());
        return model;
    }
}
