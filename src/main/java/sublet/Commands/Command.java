package sublet.Commands;

import sublet.Exceptions.BaseException;
import sublet.controllers.Controller;

import java.text.ParseException;

public interface Command {
    void Execute(Controller controller) throws BaseException, ParseException;
}
