import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/", (req,res)->{
            Map<String,Object> model = new HashMap<>();
            model.put("message","Hello World 123");
            return new ModelAndView(model, "velocity/index.vm");
        }, new VelocityTemplateEngine());
        get("/hello", (req, res) -> "Hello World");
        get("/hello/:name", (req,res)->{
            return "Hello" + req.params("name");
        });
    }
}