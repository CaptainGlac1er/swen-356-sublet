package sublet.service;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.Commands.Index;
import sublet.Commands.Listing.FilterListing;
import sublet.controllers.FilterController;
import sublet.controllers.IndexController;
import sublet.controllers.ListingController;
import sublet.util.PageRender;
import sublet.util.Path;

public class IndexService {
    public static Route serveIndexPage = (Request request, Response response) -> {
        IndexController controller = new IndexController(request, response);
        controller.Execute(new Index());
        return PageRender.render(request,controller.getModel(), Path.Template.INDEX);
    };
    public static Route filterListing = (request, response) -> {
        FilterController controller = new FilterController(request,response);
        controller.Execute(new FilterListing());
        return PageRender.render(request, controller.getModel(), Path.Template.INDEX);
    };
}
