package sublet.models;

import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.PermissionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Listings {

    public static void UpdateListing(Listing listing, User user) throws PermissionException, DatabaseException {
        if (Roles.CanModListings(user.getUserRoles()) || listing.getUser().checkIfSameUser(user)) {
            String sql = "UPDATE listingdb SET `desc`=?, rent=?, address=?, furnished=?, gender=?, housing=?, payment=?, parking=?, utilities=? WHERE lid=?";
            try (Connection con = DatabaseConnection.write.getConnection()) {
                PreparedStatement updateListing = con.prepareStatement(sql);
                int i = 1;
                updateListing.setString(i++, listing.getDesc());
                updateListing.setString(i++, listing.getRent());
                updateListing.setString(i++, listing.getAddress());
                updateListing.setString(i++, listing.getIsFurnished().name());
                updateListing.setString(i++, listing.getGender().name());
                updateListing.setString(i++, listing.getHousingType().name());
                updateListing.setString(i++, listing.getPayment_frequency().name());
                updateListing.setString(i++, listing.getParking_type().name());
                updateListing.setBoolean(i++, listing.getUtilIncluded());
                updateListing.setLong(i, listing.getLID());
                updateListing.execute();
            } catch (SQLException e) {
                throw new DatabaseException("Listing database exception", e);
            }
        } else {
            throw new PermissionException("Can't modify other posters listing");
        }
    }


    public static void RemoveListing(long lid, User user) throws PermissionException, DatabaseException {
        if (Roles.CanModListings(user.getUserRoles()) || GetListing(lid, user).getUser().checkIfSameUser(user)) {
            RemoveListing(lid);
        } else {
            throw new PermissionException("You can not delete this listing");
        }
    }

    public static void UpdateListingVisibility(Listing listing, User user) throws PermissionException, DatabaseException {
        if (Roles.CanModListings(user.getUserRoles()) || listing.getUser().checkIfSameUser(user)) {
            UpdateListingVisibility(listing);
        } else {
            throw new PermissionException("You can not delete this listing");
        }
    }

    /**
     * Removes a listing from the database with lid
     *
     * @param lid listing id of listing to remove
     */
    public static void RemoveListing(long lid) throws DatabaseException {
        String sql = "DELETE FROM `listingdb` WHERE `lid` = ?";
        try (Connection con = DatabaseConnection.write.getConnection()) {
            PreparedStatement removeListing = con.prepareStatement(sql);
            removeListing.setLong(1, lid);
            removeListing.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
    }

    /**
     * Add a listing to the database
     *
     * @param listing listing object that you want to be saved in the database
     */
    public static void AddListing(Listing listing) throws DatabaseException {
        String sql = "SELECT addListing(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) AS lid";
        try (Connection con = DatabaseConnection.write.getConnection()) {
            PreparedStatement addListing = con.prepareStatement(sql);
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
            throw new DatabaseException("Listing database exception", e);
        }
    }

    public static void UpdateListingVisibility(Listing listing) throws DatabaseException {
        try (Connection con = DatabaseConnection.write.getConnection()) {
            PreparedStatement visListing = null;
            if (listing.getListingVisibility() == Listing.ListingVisibility.ACTIVE)
                visListing = con.prepareStatement("SELECT activelisting(?)");
            else if (listing.getListingVisibility() == Listing.ListingVisibility.ARCHIVE)
                visListing = con.prepareStatement("SELECT archivelisting(?)");
            else if (listing.getListingVisibility() == Listing.ListingVisibility.RIT)
                visListing = con.prepareStatement("UPDATE `swen-356-sublet`.`listingdb` SET `visibility`=2 WHERE `lid`=?");
            if (visListing != null) {
                visListing.setLong(1, listing.getLID());
                visListing.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
    }

    //TODO will need limits as database grows add in paging

    /**
     * Gets all the listings that all users have posted
     *
     * @return an ArrayList of all the Listings objects
     */
    public static ArrayList<Listing> GetListings() throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getlistings";
        ArrayList<Listing> ret = new ArrayList<>();
        getListingsProcessing(sql, ret);
        return ret;
    }

    public static ArrayList<Listing> GetActiveListings() throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getactivelistings";
        ArrayList<Listing> ret = new ArrayList<>();
        getListingsProcessing(sql, ret);
        return ret;
    }

    public static ArrayList<Listing> GetRitListings() throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getritlistings";
        ArrayList<Listing> ret = new ArrayList<>();
        getListingsProcessing(sql, ret);
        return ret;

    }

    private static void getListingsProcessing(String sql, ArrayList<Listing> ret) throws DatabaseException {
        try (Connection con = DatabaseConnection.read.getConnection()) {
            PreparedStatement getListing = con.prepareStatement(sql);
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                ret.add(createListingFromSQL(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
    }

    /**
     * @param sql   the prepared sql statement
     * @param value The string enum we are querying
     * @return The list of lids that fit the query
     */
    private static ArrayList<Long> getLIDDatabase(String sql, String value) throws DatabaseException {
        ArrayList<Long> ret = new ArrayList<>();
        try (Connection con = DatabaseConnection.read.getConnection()) {
            PreparedStatement getListing = con.prepareStatement(sql);
            getListing.setString(1, value);
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong(1));
            }

        } catch (SQLException e) {

        }
        return ret;
    }

    /**
     * Gets the listing with the listing ID number
     *
     * @param lid listing id that it was given at creation
     * @return listing object with that listing ID number
     */
    public static Listing GetListing(long lid, User user) throws DatabaseException, PermissionException {
        Listing ret = null;
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getlistings WHERE lid = ?";
        try (Connection con = DatabaseConnection.read.getConnection()) {
            PreparedStatement getListing = con.prepareStatement(sql);
            getListing.setLong(1, lid);
            ResultSet rs = getListing.executeQuery();
            if (rs.next()) {
                ret = createListingFromSQL(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
        if (ret == null)
            return ret;
        else if (ret.getUser().checkIfSameUser(user))
            return ret;
        else if (ret.getListingVisibility() != null && ret.getListingVisibility().name().equals("RIT") && user.getUserRoles().contains(Roles.CurrentRoles.get("RIT")))
            return ret;
        else if (ret.getListingVisibility().name().equals("ACTIVE"))
            return ret;
        else
            throw new PermissionException("Can't access this listing");
    }


    public static ArrayList<Listing> GetUserRitListings(User user) throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getritlistings WHERE uid = ?";

        ArrayList<Listing> ret = new ArrayList<>();
        getUserListingsProcessing(user, sql, ret);
        return ret;
    }

    /**
     * Gets all of the listings of the user
     *
     * @param user the user of the listings
     * @return All of the listings that the user made
     */
    public static ArrayList<Listing> GetUserListings(User user) throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getlistings WHERE uid = ?";

        ArrayList<Listing> ret = new ArrayList<>();
        getUserListingsProcessing(user, sql, ret);
        return ret;
    }

    /**
     * Gets all active listings of the user
     *
     * @param user the user of the listings
     * @return Active listings that the user made
     */
    public static ArrayList<Listing> GetUserActiveListings(User user) throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getlistings WHERE uid = ? AND visibility = 'ACTIVE'";

        ArrayList<Listing> ret = new ArrayList<>();
        getUserListingsProcessing(user, sql, ret);
        return ret;
    }


    /**
     * Gets all archived listings of the user
     *
     * @param user the user of the listings
     * @return Archived listings that the user made
     */
    public static ArrayList<Listing> GetUserArchiveListings(User user) throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM getlistings WHERE uid = ? AND visibility = 'ARCHIVE'";

        ArrayList<Listing> ret = new ArrayList<>();
        getUserListingsProcessing(user, sql, ret);
        return ret;
    }

    private static void getUserListingsProcessing(User user, String sql, ArrayList<Listing> ret) throws DatabaseException {
        try (Connection con = DatabaseConnection.read.getConnection()) {
            PreparedStatement getListing =
                    con.prepareStatement(sql);
            getListing.setLong(1, user.getUID());
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                Listing cur = createListingFromSQL(rs);
                ret.add(cur);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
    }

    public static ArrayList<Listing> GetUserFavoritedListings(User user) throws DatabaseException {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities, visibility FROM `swen-356-sublet`.`getfavlistings` WHERE fuid = ?";
        ArrayList<Listing> ret = new ArrayList<>();
        getUserListingsProcessing(user, sql, ret);
        for (int i = 0; i < ret.size(); i++)
            ret.get(i).setFavorited(true);
        return ret;
    }

    public static void AddUserFavoriteListing(User user, Listing listing) throws DatabaseException {
        String sql = "SELECT favlisting(?,?)";
        processFavoriteSQL(user, listing, sql);
    }

    public static void RemoveUserFavoriteListing(User user, Listing listing) throws DatabaseException {
        String sql = "SELECT unfavlisting(?,?)";
        processFavoriteSQL(user, listing, sql);
    }

    private static void processFavoriteSQL(User user, Listing listing, String sql) throws DatabaseException {
        try {
            PreparedStatement addFavorite = DatabaseConnection.write.getConnection().prepareStatement(sql);
            addFavorite.setLong(1, user.getUID());
            addFavorite.setLong(2, listing.getLID());
            addFavorite.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Listing database exception", e);
        }
    }


    /**
     * Helper function to create the listings object from the sql select row
     *
     * @param rs result set from the sql query
     * @return the Listing object
     * @throws SQLException Throws an exception when a field was not found
     */
    private static Listing createListingFromSQL(ResultSet rs) throws SQLException, DatabaseException {
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
        ret.setListingVisibility(Listing.getVisibilityValue(rs.getString("visibility")));
        return ret;
    }

}
