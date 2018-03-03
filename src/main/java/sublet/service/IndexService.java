package sublet.service;

import spark.Request;
import spark.Response;
import spark.Route;
import sublet.Commands.Index;
import sublet.controllers.IndexController;
import sublet.util.PageRender;
import sublet.util.Path;

public class IndexService {
    public static Route serveIndexPage = (Request request, Response response) -> {
        IndexController controller = new IndexController(request, response);
        controller.Execute(new Index());
        return PageRender.render(request,controller.getModel(), Path.Template.INDEX);
    };
}
