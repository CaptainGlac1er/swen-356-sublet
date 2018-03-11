package sublet.models;

import sublet.Exceptions.PermissionException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Listings {
    //private static HashMap<Long,Listing> Listings = new HashMap<>();
    public static void AddListing(Listing listing){
        AddListingDB(listing);
    }
    public static ArrayList<Listing> GetListings(){
        return GetListingsDB();
    }

    public static ArrayList<Listing> GetUserListings(User user) {
        //would be a sql statement in the future
        return GetUserListingsDB(user);
    }

    public static void UpdateListing(Listing listing, User user) throws PermissionException {
        if (Roles.CanModListings(user.getUserRoles()) || listing.getUser().checkIfSameUser(user)) {
            RemoveListingDB(listing.getLID());
            AddListingDB(listing);
        } else {
            throw new PermissionException("Can't modify other posters listing");
        }
    }

    public static Listing GetListing(long lid) {
        return GetListingDB(lid);
    }

    public static void RemoveListing(long lid, User user) throws PermissionException {
        if (Roles.CanModListings(user.getUserRoles()) || GetListingDB(lid).getUser().checkIfSameUser(user)) {
            RemoveListingDB(lid);
        } else {
            throw new PermissionException("You can not delete this listing");
        }
    }

    private static void RemoveListingDB(long lid) {
        try {
            PreparedStatement removeListing = DatabaseConnection.write.getConnection().prepareStatement("DELETE FROM `listingdb` WHERE `lid` = ?");
            removeListing.setLong(1, lid);
            removeListing.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Listings.remove(lid);
    }

    private static void AddListingDB(Listing listing) {
        try {
            PreparedStatement addListing = DatabaseConnection.write.getConnection().prepareStatement("SELECT addListing(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AS lid");
            addListing.setLong(1, listing.getUser().getUID());
            addListing.setString(2, listing.getDesc());
            addListing.setString(3, listing.getRent());
            addListing.setString(4, listing.getAddress());
            addListing.setString(5, listing.getIsFurnished().name());
            addListing.setString(6, listing.getGender().name());
            addListing.setString(7, listing.getHousingType().name());
            addListing.setString(8, listing.getPayment_frequency().name());
            addListing.setString(9, listing.getParking_type().name());
            addListing.setBoolean(10, listing.getUtilIncluded());
            ResultSet rs = addListing.executeQuery();
            if (rs.next())
                listing.setLid(rs.getLong("lid"));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        //Listings.put(listing.getLID(), listing);
    }

    private static ArrayList<Listing> GetListingsDB() {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities FROM getlistings";
        ArrayList<Listing> ret = new ArrayList<>();
        try {
            PreparedStatement getListing =
                    DatabaseConnection.read.getConnection().prepareStatement(sql);
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                ret.add(createListingFromSQL(rs));
            }

        } catch (SQLException e) {

        }
        return ret;
        //return new ArrayList<Listing>(Listings.values());
    }

    private static Listing GetListingDB(long lid) {
        Listing ret = null;
        try {
            String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities FROM getlistings WHERE lid = ?";
            PreparedStatement getListing =
                    DatabaseConnection.read.getConnection().prepareStatement(sql);
            getListing.setLong(1, lid);
            ResultSet rs = getListing.executeQuery();
            if (rs.next()) {
                ret = createListingFromSQL(rs);
            }
        } catch (SQLException e) {

        }
        return ret;
        //return Listings.get(lid);
    }

    private static ArrayList<Listing> GetUserListingsDB(User user) {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities FROM getlistings WHERE uid = ?";
        ArrayList<Listing> ret = new ArrayList<>();
        try {
            PreparedStatement getListing =
                    DatabaseConnection.read.getConnection().prepareStatement(sql);
            getListing.setLong(1, user.getUID());
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                ret.add(createListingFromSQL(rs));
            }

        } catch (SQLException e) {

        }
        return ret;
        /*ArrayList<Listing> userListings = new ArrayList<>();
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
        return userListings;*/
    }

    private static Listing createListingFromSQL(ResultSet rs) throws SQLException {
        Listing ret = new Listing();
        ret.setLid(rs.getLong("lid"));
        ret.setUser(Users.getCurrentUserUID(rs.getLong("uid")));
        ret.setDesc(rs.getString("desc"));
        ret.setRent(rs.getString("rent"));
        ret.setAddress(rs.getString("address"));
        ret.setFurnished(Listing.getIs_furnished(rs.getString("furnished")));
        ret.setGender(Listing.getGenderValue(rs.getString("gender")));
        ret.setHousingType(Listing.getHousing_type(rs.getString("housing")));
        ret.setPayment(Listing.getPaymentValue(rs.getString("payment")));
        ret.setParkingType(Listing.getParkingValue(rs.getString("parking")));
        ret.setUtilIncluded(rs.getBoolean("utilities"));
        return ret;
    }
}
