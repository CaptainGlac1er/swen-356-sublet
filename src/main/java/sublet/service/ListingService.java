package sublet.service;

import spark.Route;
import sublet.controllers.Controller;
import sublet.controllers.ListingController;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingService {
    public static Route listingsForumPage = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.getModel().put("gender",Listing.genderLst);
        controller.getModel().put("housing",Listing.housingLst);
        controller.getModel().put("furnished",Listing.furnishedLst);
        controller.getModel().put("payment",Listing.paymentLst);
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route listingsPost = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        Listing L = new Listing("Meme", request.queryParams("dis"),request.queryParams("rent"),
                Listing.selectPayment(request.queryParams("payment")), Listing.selectGender(request.queryParams("gender")),
                Listing.selectHousing(request.queryParams("housing")), Listing.selectFurnished(request.queryParams("furnished")));
        controller.addListing(L);
        controller.getModel().put("listings",controller.getListings());
        return PageRender.render(request, controller.getModel(), Path.Template.INDEX);
    };
}
