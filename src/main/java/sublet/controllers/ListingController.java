package sublet.controllers;
import spark.Request;
import spark.Response;
import sublet.Commands.Command;
import sublet.Exceptions.BaseException;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

public class ListingController extends Controller {

    public ListingController(Request request, Response response) {
        super(request, response);
        model.put("gender",Listing.genderList);
        model.put("payment",Listing.paymentList);
        model.put("house",Listing.housingList);
        model.put("furn",Listing.furnishedList);
    }

    @Override
    public void Execute() {

    }

    @Override
    public void Execute(Command command) {
        try {
            command.Execute(this);
        }catch (BaseException be){

        }
    }
}
