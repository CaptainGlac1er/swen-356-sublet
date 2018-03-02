import org.apache.velocity.app.VelocityEngine;
import spark.Spark;
import sublet.controllers.ListingController;
import sublet.models.CurrentUser;
import sublet.models.Listing;
import sublet.models.Listings;
import sublet.models.StandardUser;
import sublet.service.IndexService;
import sublet.service.ListingService;
import sublet.service.UserService;
import sublet.util.Path;


import java.sql.*;
import java.util.Date;
import java.util.Random;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Spark.staticFiles.location("/static");
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty("runtime.references.strict", true);
        ve.init();
        StandardUser user = new StandardUser(1234, "Bob", "Name", "user", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);
        user = new StandardUser(12345, "Bill", "Name", "user2", "qwerty", "td@rit.edu", new Date(12345), new Date(734324));
        CurrentUser.registerUser(user);


        port(4000);
        String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse luctus augue nec sollicitudin aliquam. Maecenas id viverra velit. Nam molestie finibus urna a iaculis. Sed non venenatis urna. Vestibulum vestibulum enim justo, quis dictum mauris mollis quis. Quisque malesuada nulla quis mollis mollis. Vivamus sed feugiat neque. Fusce vel leo vitae est laoreet venenatis. ";

        Listing listing = new Listing((new Random()).nextLong(), CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);
        listing = new Listing((new Random()).nextLong(),CurrentUser.getCurrentUserUID(1234), desc, "$500/month",
                "55 Ocean Street",Listing.PaymentFrequencyOptions.MONTHLY, Listing.GenderOptions.MALE,
                Listing.HousingTypeOptions.PARKPOINT, Listing.IsFurnishedOptions.FURNISHED);
        Listings.AddListing(listing);

        get(Path.Web.INDEX, IndexService.serveIndexPage);
        get(Path.Web.USER, UserService.serveIndexPage);

        get(Path.Web.LOGIN, UserService.serveLoginPage);
        post(Path.Web.LOGIN, UserService.serveLogin);
        get(Path.Web.LOGOUT, UserService.serveLogout);

        get(Path.Web.NEWLISTING, ListingService.listingsForumPage);
        post(Path.Web.NEWLISTING, ListingService.addListing);
        post(Path.Web.EDITLISTING, ListingService.editListing);
        post(Path.Web.REMOVELISTING,ListingService.removeListing);
        post(Path.Web.MODIFYLISTING, ListingService.modifyListing);

        get(Path.Web.NEWUSER, UserService.serveCreateUser);
        post(Path.Web.NEWUSER, UserService.serveUser);

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/TigerTenant", "sa", "");
        // add application code here
        conn.close();
    }
}