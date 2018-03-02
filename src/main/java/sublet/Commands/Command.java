package sublet.Commands;

import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;

public interface Command {
    void Execute(Controller controller) throws BaseException;
}
