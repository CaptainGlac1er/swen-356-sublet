package sublet.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.objects.Item;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.HashMap;
import java.util.Map;

public class AboutController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        return PageRender.render(request,model, Path.Template.ABOUT);
    };
}
