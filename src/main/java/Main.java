import sublet.controllers.ListingController;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.util.Path;


import java.sql.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        port(4000);
        get(Path.Web.INDEX, IndexService.serveIndexPage);
        get(Path.Web.NEWLISTING, ListingService.listingsForumPage);
        post(Path.Web.NEWLISTING, ListingService.listingsPost);


        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
    }
}