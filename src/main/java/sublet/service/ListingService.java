package sublet.service;

import spark.Route;
import sublet.Commands.Listing.*;
import sublet.Commands.LoggedInCommand;
import sublet.controllers.ListingController;
import sublet.util.PageRender;
import sublet.util.Path;

public class ListingService {
    public static Route formListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new LoggedInCommand());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);
    };
    public static Route addListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new AddListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);
    };
    public static Route viewListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new ViewListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route editListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new EditListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route modifyListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new ModifyListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route removeListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new RemoveListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route changeVisibilityListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new SetVisibilityListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route addFavoriteListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new FavoriteListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };
    public static Route removeFavoriteListing = (request, response) -> {
        ListingController controller = new ListingController(request, response);
        controller.Execute(new UnFavoriteListing());
        return PageRender.render(request, controller.getModel(), Path.Template.LISTING);

    };


}
