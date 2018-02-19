package sublet.controllers;
import spark.Route;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingController {
    public static ArrayList<Listing> Listings = new ArrayList<Listing>();
    public static Route listingsForumPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("currentuser", CurrentUser.getCurrentUser());
        return PageRender.render(request, model, Path.Template.NEWLISTING);
    };
    public static Route listingsPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("currentuser", CurrentUser.getCurrentUser());
        Listing L = new Listing("Meme", request.queryParams("dis"),request.queryParams("rent"),
                Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
               Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.add((L));
        System.out.println(Listings.size());
        return PageRender.render(request, model, Path.Template.NEWLISTING);
    };
}
