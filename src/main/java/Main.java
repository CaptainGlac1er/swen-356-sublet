import sublet.controllers.ListingController;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.util.Path;


import java.sql.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        port(4000);
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";

        Listing listing = new Listing("user123@rit.edu", desc, "$500/month",
                Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);
        Listings.AddListing(listing);

        get(Path.Web.INDEX, IndexService.serveIndexPage);
        get(Path.Web.NEWLISTING, ListingService.listingsForumPage);
        post(Path.Web.NEWLISTING, ListingService.listingsPost);


        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
    }
}