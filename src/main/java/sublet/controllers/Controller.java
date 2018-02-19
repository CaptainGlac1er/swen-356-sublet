package sublet.controllers;

import spark.Request;
import spark.Response;
import sublet.models.CurrentUser;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    Map<String, Object> model;
    public Controller(Request request, Response response){
            model = new HashMap<>();
            model.put("currentuser", CurrentUser.getCurrentUser(request, response));

    }
    public Map<String, Object> getModel(){
        return model;
    }
}
