import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.service.UserService;
import sublet.util.Path;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Random;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        staticFiles.location("/static");
        port(4000);


        initTestData();
        setupRouting();



        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
        awaitInitialization();
    }

    static void initTestData(){
        StandardUser user = new StandardUser(1234, "Bob", "Name", "user", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);
        user = new StandardUser(12345, "Bill", "Name", "user2", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);

        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";

        Listing listing = new Listing((new Random()).nextLong(), CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(12345), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(12345), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
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
                path("", ()->{

                });
                path("/create", ()->{
                    get("",ListingService.listingsForumPage);
                    post("",ListingService.addListing);
                });
                path("/:lid",()->{
                    get("/edit",ListingService.editListing);
                    post("/edit",ListingService.modifyListing);
                    get("/remove",ListingService.removeListing);
                });
            });

        });
    }

}