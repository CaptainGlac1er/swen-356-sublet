package sublet.Commands.Listing;

import spark.Request;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;
import sublet.models.Listings;

import java.util.ArrayList;

public class FilterListing implements Command{

    public void Execute(Controller controller) throws BaseException{
        Request request = controller.getCurrentRequest();
        if(!request.queryParams("gender").equals("")){
            controller.addToModel("listings",Listings.FilterListing(request));

        }
        else{
            controller.addToModel("listings", Listings.GetListings());
        }
    }
}
