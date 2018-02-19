package sublet.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.StandardUser;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IndexController extends Controller {
    public IndexController(Request request, Response response) {
        super(request, response);
    }
}
