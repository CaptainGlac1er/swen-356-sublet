package sublet.service;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.Commands.Index;
import sublet.Commands.LoggedInCommand;
import sublet.Commands.User.AddUser;
import sublet.Commands.User.LogInUser;
import sublet.Commands.User.LogOutUser;
import sublet.controllers.Controller;
import sublet.controllers.UserController;
import sublet.util.PageRender;
import sublet.util.Path;

public class UserService {
    public static Route serveIndexPage = (Request request, Response response) -> { //get
        Controller controller = new UserController(request, response);
        controller.Execute(new LoggedInCommand());
        return PageRender.render(request, controller.getModel(), Path.Template.USER);
    };
    public static Route serveLoginPage = (Request request, Response response) -> { //get
        Controller controller = new UserController(request, response);
        controller.Execute(new Index());
        return PageRender.render(request, controller.getModel(), Path.Template.LOGIN);
    };
    public static Route serveLogin = (Request request, Response response) -> { //post
        Controller controller = new UserController(request, response);
        controller.Execute(new LogInUser());
        return PageRender.render(request, controller.getModel(), Path.Template.LOGIN);
    };
    public static Route serveLogout = (Request request, Response response) -> { //get
        Controller controller = new UserController(request, response);
        controller.Execute(new LogOutUser());
        return PageRender.render(request, controller.getModel(), Path.Template.LOGIN);
    };
    public static Route serveCreateUser = (Request request, Response response) -> { //get
        Controller controller = new UserController(request, response);
        controller.Execute(new Index());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWUSER);
    };
    public static Route serveUser = (Request request, Response response) -> { //post
        Controller controller = new UserController(request, response);
        controller.Execute(new AddUser());
        return PageRender.render(request, controller.getModel(), Path.Template.NEWUSER);
    };
}
