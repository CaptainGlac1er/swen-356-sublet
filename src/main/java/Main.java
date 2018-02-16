import org.apache.velocity.app.Velocity;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import sublet.controllers.AboutController;
import sublet.controllers.IndexController;
import sublet.objects.*;
import sublet.util.Path;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4000);
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.ABOUT, AboutController.serveIndexPage);
        get("/hello", (req, res) -> "Hello World");
        get("/hello/:name", (req,res)->{
            return "Hello" + req.params("name");
        });
    }
}