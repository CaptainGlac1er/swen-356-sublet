import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import sublet.objects.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/", (req,res)->{
            Map<String,Object> model = new HashMap<>();
            Item item = new Item("Hello Test","This is a description");
            model.put("message",item);
            return new ModelAndView(model, "velocity/index.vm");
        }, new VelocityTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/hello/:name", (req,res)->{
            return "Hello" + req.params("name");
        });
    }
}