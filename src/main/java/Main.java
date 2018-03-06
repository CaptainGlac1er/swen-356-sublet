import org.h2.tools.RunScript;
import org.h2.tools.Server;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
            System.out.println(e);
        }



        Connection conn = DriverManager.getConnection(prop.getProperty("database"), prop.getProperty("readuser"), prop.getProperty("readpass"));
        Statement test = conn.createStatement();
        ResultSet res = test.executeQuery("select * from userdb;");
        while(res.next())
            System.out.println(res.getString("email"));

        initTestData();
        setupRouting();
        // add application code here
        conn.close();
        awaitInitialization();
    }

//    private void buildTables() throws SQLException{
//        InputStream in = getClass().getResourceAsStream("tables.sql");
//        BufferedReader reader =  new BufferedReader(new InputStreamReader(in));
//        RunScript.execute(conn, reader);
//    }

    static void initTestData(){
        StandardUser user = new StandardUser(1234, "Bob", "Name", "user", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);
        user = new StandardUser(12345, "Bill", "Name", "user2", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);

        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";

        Listing listing = new Listing((new Random()).nextLong(), CurrentUser.getCurrentUserUID(1234), desc, "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, false);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(12345), desc, "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, true);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, false);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(12345), desc, "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, false);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "500",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED, Listing.ParkingOption.ON_STR, true);
        Listings.AddListing(listing);

    }
    static void setupRouting(){
        path("/", ()->{
            before((req, res)->{
                String path = req.pathInfo();
                if(path.endsWith("/"))
                    res.redirect(path.substring(0, path.length() -1));
            });
            path("",()-> get("",IndexService.serveIndexPage));
            path("/user", ()->{
                path("",()->{
                    get("",UserService.serveIndexPage);
                });
                path("/login", ()->{
                    get("",UserService.serveLoginPage);
                    post("",UserService.serveLogin);
                });
                path("/logout", ()->{
                    get("",UserService.serveLogout);
                    //post("",UserService.serveLogout);
                });
                path("/register", ()->{
                    get("",UserService.serveCreateUser);
                    post("",UserService.serveUser);
                });
            });
            path("/listing", ()->{
                path("/create", ()->{
                    get("",ListingService.formListing);
                    post("",ListingService.addListing);
                });
                path("/:lid",()->{
                    get("",ListingService.viewListing);
                    get("/edit",ListingService.editListing);
                    post("/edit",ListingService.modifyListing);
                    get("/remove",ListingService.removeListing);
                });
            });

        });
    }

}