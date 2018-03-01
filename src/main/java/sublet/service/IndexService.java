package sublet.service;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.controllers.Controller;
import sublet.controllers.IndexController;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndexService {
    public static Route serveIndexPage = (Request request, Response response) -> {
        IndexController controller = new IndexController(request, response);

        return PageRender.render(request,controller.getModel(), Path.Template.INDEX);
    };
}
