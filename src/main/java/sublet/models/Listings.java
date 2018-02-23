package sublet.models;

import java.util.ArrayList;

public class Listings {
    private static ArrayList<Listing> Listings = new ArrayList<>();
    public static void AddListing(Listing listing){
        Listings.add(listing);
    }
    public static ArrayList<Listing> GetListings(){
        return Listings;
    }
    public static void RemoveListing(Listing listing){
        Listings.remove(listing);
    }
}
