package sublet.util;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;
import sublet.models.CurrentUser;

import java.util.Map;


public class PageRender {
    public static String render(Request request, Map<String, Object> model, String templatePath) {

        //model.put("msg", new MessageBundle(getSessionLocale(request)));

        //model.put("currentUser", CurrentUser.getSessionCurrentUser(request));

        //model.put("WebPath", Path.Web.class); // Access application URLs from templates

        return strictVelocityEngine().render(new ModelAndView(model, templatePath));

    }


    private static VelocityTemplateEngine strictVelocityEngine() {

        VelocityEngine configuredEngine = new VelocityEngine();

        configuredEngine.setProperty("runtime.references.strict", true);

        configuredEngine.setProperty("resource.loader", "class");

        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        return new VelocityTemplateEngine(configuredEngine);

    }
}
