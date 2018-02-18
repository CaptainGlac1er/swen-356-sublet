package sublet.controllers;
import spark.Request;
import spark.Response;
import spark.Route;
import sublet.objects.Item;
import sublet.util.PageRender;
import sublet.util.Path;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ListingController {
    public static Route listingsForumPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        return PageRender.render(request, model, "/velocity/pages/listingform.vm");
    };
}
