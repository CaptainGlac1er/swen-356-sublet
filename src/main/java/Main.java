import sublet.controllers.ListingController;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.service.UserService;
import sublet.util.Path;


import java.sql.*;
import java.util.Date;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        port(4000);
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";

        Listing listing = new Listing(new StandardUser(1234, "Test", "Name", "TU", "qwerty", "td@rit.edu", new Date(12345), new Date(3456)), desc, "$500/month",
                Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);

        get(Path.Web.INDEX, IndexService.serveIndexPage);
        get(Path.Web.NEWLISTING, ListingService.listingsForumPage);
        get(Path.Web.USER, UserService.serveIndexPage);
        post(Path.Web.NEWLISTING, ListingService.listingsPost);


        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
    }
}