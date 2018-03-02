package sublet.service;

import spark.Route;
import sublet.Commands.Listing.AddListing;
import sublet.Commands.Listing.EditListing;
import sublet.Commands.Listing.ModifyListing;
import sublet.Commands.Listing.RemoveListing;
import sublet.controllers.ListingController;
import sublet.util.PageRender;
import sublet.util.Path;

public class ListingService {
    public static Route listingsForumPage = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute();
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route listingsPost = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute();
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route addListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new AddListing());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);
    };
    public static Route editListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new EditListing());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);

    };
    public static Route modifyListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new ModifyListing());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);

    };
    public static Route removeListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new RemoveListing());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWLISTING);

    };
}
