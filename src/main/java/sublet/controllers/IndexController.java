package sublet.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();

        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";


        Listing listing = new Listing("user123@rit.edu", desc, "$500/month", Listing.GenderOptions.MALE,
               Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);

        ArrayList<Listing> listings = new ArrayList<>();
        listings.add((listing));
        listings.add((listing));
        listings.add((listing));
        listings.add((listing));

        model.put("listings",listings);
        return PageRender.render(request,model, Path.Template.INDEX);
    };
}
