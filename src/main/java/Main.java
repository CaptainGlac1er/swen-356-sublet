import sublet.models.DatabaseConnection;
import sublet.models.Logging;
import sublet.models.Roles;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.service.UserService;

import java.io.InputStream;
import java.util.Properties;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        staticFiles.location("/static");
        port(4000);
        Class.forName("org.h2.Driver");
        Properties prop = new Properties();
        InputStream configStream = Main.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(configStream);
        } catch (Exception e) {
            Logging.HandleException(e);
        }

        DatabaseConnection.read = new DatabaseConnection(prop.getProperty("database"), prop.getProperty("readuser"), prop.getProperty("readpass"));
        DatabaseConnection.write = new DatabaseConnection(prop.getProperty("database"), prop.getProperty("defaultuser"), prop.getProperty("defaultpass"));

        setupDefaultRoles();
        //initTestData();
        setupRouting();
        // add application code here
        awaitInitialization();
        init();
    }


    static void setupDefaultRoles() {
        Roles.MakeStandardRoles();
    }

    static void setupRouting() {
        path("/", () -> {
            before((req, res) -> {
                String path = req.pathInfo();
                if (path.endsWith("/"))
                    res.redirect(path.substring(0, path.length() - 1));
            });
            path("", () -> {
                get("", IndexService.serveIndexPage);
                post("", IndexService.filterListing);
            });
            path("/user", () -> {
                path("", () -> {
                    get("", UserService.serveIndexPage);
                });
                path("/login", () -> {
                    get("", UserService.serveLoginPage);
                    post("", UserService.serveLogin);
                });
                path("/logout", () -> {
                    get("", UserService.serveLogout);
                    //post("",UserService.serveLogout);
                });
                path("/register", () -> {
                    get("", UserService.serveCreateUser);
                    post("", UserService.serveUser);
                });
            });
            path("/listing", () -> {
                path("/create", () -> {
                    get("", ListingService.formListing);
                    post("", ListingService.addListing);
                });
                path("/:lid", () -> {
                    get("", ListingService.viewListing);
                    get("/edit", ListingService.editListing);
                    post("/edit", ListingService.modifyListing);
                    get("/remove", ListingService.removeListing);
                    post("/visibility", ListingService.changeVisibilityListing);
                    get("/fav", ListingService.addFavoriteListing);
                    get("/unfav", ListingService.removeFavoriteListing);
                });
            });

        });
    }

}