package sublet.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.objects.Item;
import sublet.util.PageRender;
import sublet.util.Path;

import java.util.HashMap;
import java.util.Map;

public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        Item item = new Item("Hello Test","This is a description");
        model.put("message",item);
        return PageRender.render(request,model, Path.Template.INDEX);
    };
}
