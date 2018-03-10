package sublet.models;

import sublet.Exceptions.PermissionException;

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
            if (l.getUser().checkIfSameUser(user)) {
                boolean hasRole = false;
                for (Role role : user.getUserRoles())
                    if (hasRole = role.isSeePosts())
                        break;
                if (hasRole) {
                    userListings.add(l);
                }
            }
        }
        return userListings;
    }

    public static void UpdateListing(Listing listing, User user) throws PermissionException {
        if (listing.getUser().checkIfSameUser(user)) {
            Listings.remove(listing.getLID());
            Listings.put(listing.getLID(), listing);
        } else {
            throw new PermissionException("Can't modify other posters listing");
        }
    }
    public static Listing GetListing(long lid, User user){
        return Listings.get(lid);
    }
    public static void RemoveListing(long lid, User user) throws PermissionException {
        if (Listings.get(lid).getUser().checkIfSameUser(user)) {
            Listings.remove(lid);
        }else{
            throw new PermissionException("You can not delete this listing");
        }
    }
}
