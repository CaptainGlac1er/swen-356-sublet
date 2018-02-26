package sublet.Commands;

import spark.Request;
import spark.Response;
import sublet.controllers.Controller;
import sublet.models.User;

public interface Command {
    void Execute(Request request, Response response, Controller controller);
}
