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
        controller.Execute();
        //controller.addListing();
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route removePost = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.removeListing();
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);

    };
}
