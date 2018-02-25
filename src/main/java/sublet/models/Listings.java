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
    public static ArrayList<Listing> GetListings(User user){
        //would be a sql statement in the future
        ArrayList<Listing> userListings = new ArrayList<>();
        for (Listing l:
             Listings) {
            if(l.getUser() instanceof  StandardUser &&
                    user instanceof  StandardUser &&
                    ((StandardUser) l.getUser()).checkIfSameUser((StandardUser)user)) {
                userListings.add(l);
            }

        }
        return userListings;
    }
    public static void RemoveListing(Listing listing){
        Listings.remove(listing);
    }
}
