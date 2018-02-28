package sublet.Commands;

import spark.Request;
import spark.Response;
import sublet.Exceptions.BaseException;
import sublet.Exceptions.PermissionException;
import sublet.controllers.Controller;
import sublet.models.User;

public interface Command {
    void Execute(Controller controller) throws BaseException;
}
