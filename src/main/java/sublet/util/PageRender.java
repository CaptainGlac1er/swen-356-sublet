package sublet.util;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import spark.ModelAndView;
import spark.Request;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;


public class PageRender {
    public static String render(Request request, Map<String, Object> model, String templatePath) {
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }


    private static VelocityTemplateEngine strictVelocityEngine() {

        VelocityEngine configuredEngine = new VelocityEngine();

        //configuredEngine.setProperty("runtime.references.strict", true);

        configuredEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");

        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        configuredEngine.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, "org.apache.velocity.app.event.implement.IncludeRelativePath");

        return new VelocityTemplateEngine(configuredEngine);

    }
}
