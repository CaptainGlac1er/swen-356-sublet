package sublet.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Listings {
    private static HashMap<Long,Listing> Listings = new HashMap<>();
    public static void AddListing(Listing listing){
        Listings.put(listing.getLID(),listing);
    }
    public static ArrayList<Listing> GetListings(){
        return new ArrayList<>(Listings.values());
    }
    public static ArrayList<Listing> GetListings(User user){
        //would be a sql statement in the future
        ArrayList<Listing> userListings = new ArrayList<>();
        for (Listing l:
             Listings.values()) {
            if(l.getUser() instanceof  StandardUser &&
                    user instanceof  StandardUser &&
                    ((StandardUser) l.getUser()).checkIfSameUser((StandardUser)user)) {
                userListings.add(l);
            }
        }
        return userListings;
    }
    public static void UpdateListing(Listing listing, User user){
        if(user instanceof StandardUser && ((StandardUser)Listings.get(listing.getLID()).getUser()).checkIfSameUser((StandardUser)user)) {
            Listings.remove(listing.getLID());
            Listings.put(listing.getLID(), listing);
        }
    }
    public static Listing GetListing(long lid, User user){
        if(user instanceof StandardUser && ((StandardUser)Listings.get(lid).getUser()).checkIfSameUser((StandardUser)user)) {
            return Listings.get(lid);
        }
        return null;
    }
    public static void RemoveListing(long lid, User user){
        if(user instanceof StandardUser && ((StandardUser)Listings.get(lid).getUser()).checkIfSameUser((StandardUser)user)) {
            Listings.remove(lid);
        }
    }
}
