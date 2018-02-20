package sublet.service;

import spark.Route;
import sublet.controllers.Controller;
import sublet.controllers.ListingController;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.HashMap;
import java.util.Map;

public class ListingService {
    public static Route listingsForumPage = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route listingsPost = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        Listing L = new Listing("Meme", request.queryParams("dis"),request.queryParams("rent"),
                Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        controller.addListing(L);
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
}
