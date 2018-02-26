package sublet.service;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.Commands.LogInUser;
import sublet.Commands.LogOutUser;
import sublet.controllers.Controller;
import sublet.controllers.IndexController;
import sublet.controllers.UserController;
import sublet.models.Listing;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Controller controller = new UserController(request, response);
        controller.Execute();
        return PageRender.render(request,controller.getModel(), Path.Template.USER);
    };
    public static Route serveLoginPage = (Request request, Response response) -> {
        Controller controller = new UserController(request, response);
        controller.Execute();
        return PageRender.render(request,controller.getModel(), Path.Template.LOGIN);
    };
    public static Route serveLogin = (Request request, Response response) -> {
        Controller controller = new UserController(request, response);
        controller.Execute(new LogInUser());
        return PageRender.render(request,controller.getModel(), Path.Template.LOGIN);
    };;
    public static Route serveLogout = (Request request, Response response) -> {
        Controller controller = new UserController(request, response);
        controller.Execute(new LogOutUser());
        return PageRender.render(request,controller.getModel(), Path.Template.LOGIN);
    };;
}