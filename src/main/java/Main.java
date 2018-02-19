import org.apache.velocity.app.Velocity;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import sublet.controllers.AboutController;
import sublet.controllers.IndexController;
import sublet.controllers.ListingController;
import sublet.objects.*;
import sublet.util.Path;
import sublet.models.Listing;


import java.sql.*;

import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        port(4000);
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.ABOUT, AboutController.serveIndexPage);
        get(Path.Web.NEWLISTING, ListingController.listingsForumPage);
        post(Path.Web.NEWLISTING, ListingController.listingsPost);
//        post(Path.Web.NEWLISTING, ((request, response) -> {
//            Map<String,Object> map = new HashMap<>();
//
//        });
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
    }
}