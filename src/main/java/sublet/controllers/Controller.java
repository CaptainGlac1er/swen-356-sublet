package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.CurrentUser;

import java.util.Map;

public class Controller {
    protected static void AddCurrentState(Map<String,Object> additions, Request request, Response response){
        additions.put("currentuser", CurrentUser.getCurrentUser(request, response));

    }
}
