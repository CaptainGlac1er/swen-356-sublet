package sublet.controllers;
import spark.Route;
import sublet.util.PageRender;

import java.util.HashMap;
import java.util.Map;

public class ListingController {
    public static Route listingsForumPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return PageRender.render(request, model, "/velocity/pages/new.vm");
    };
}
