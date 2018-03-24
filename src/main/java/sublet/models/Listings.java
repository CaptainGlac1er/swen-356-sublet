package sublet.models;

import spark.Request;
import sublet.Exceptions.DatabaseException;
import sublet.Exceptions.PermissionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Listings {



    public static void AddUserFavoriteListing(User user, Listing listing) {
        addUserFavoriteListing(user, listing);
    }

    public static void RemoveUserFavoriteListing(User user, Listing listing) {
        removeUserFavoriteListing(user, listing);
    }

    /**
     * This method is used to manage how the filtering queries the database
     * @param request The request object
     * @return The list of listings that fit the query
     */
    public static ArrayList<Listing> FilterListing(Request request) throws DatabaseException {
        ArrayList<Long> gender_list = new ArrayList<>();
        ArrayList<Long> housing_options_list = new ArrayList<>();
        ArrayList<Listing> res = new ArrayList<>();
        String gender = request.queryParams("gender");                      //Sets the gender query param
        String housing = request.queryParams("housing_options");            //Sets the housing query param
        if (gender.equals("") && housing.equals("")){                       //if neither are set, return active list
            return GetActiveListings();
        }

        if (!gender.equals("") && !housing.equals("")){                     //if both are set get intersection
            gender_list = GenderFilteredListing(gender);
            housing_options_list = HousingOptionsFilteredListing(housing);
            ArrayList<Long> filtered_list = gender_list;
            filtered_list.retainAll(housing_options_list);
            for(Long lid : filtered_list){
                res.add(GetListing(lid));
            }
        }
        else if((!gender.equals(""))){
            gender_list = GenderFilteredListing(gender);
            for(Long lid : gender_list){
                res.add(GetListing(lid));
            }
        }
        else if(!housing.equals("")){
            housing_options_list = HousingOptionsFilteredListing(housing);
            for(Long lid : housing_options_list){
                res.add(GetListing(lid));
            }
        }
        return res;
    }


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
            }
        } else {
            throw new PermissionException("Can't modify other posters listing");
        }
    }


    public static void RemoveListing(long lid, User user) throws PermissionException, DatabaseException {
        if (Roles.CanModListings(user.getUserRoles()) || GetListing(lid).getUser().checkIfSameUser(user)) {
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
    public static void RemoveListing(long lid) {
        String sql = "DELETE FROM `listingdb` WHERE `lid` = ?";
        try (Connection con = DatabaseConnection.write.getConnection()) {
            PreparedStatement removeListing = con.prepareStatement(sql);
            removeListing.setLong(1, lid);
            removeListing.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a listing to the database
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
            System.out.println(e.toString());
        }
    }

    public static void UpdateListingVisibility(Listing listing) {
        try (Connection con = DatabaseConnection.write.getConnection()) {
            PreparedStatement visListing = null;
            if (listing.getListingVisibility() == Listing.ListingVisibility.ACTIVE)
                visListing = con.prepareStatement("SELECT activelisting(?)");
            else if (listing.getListingVisibility() == Listing.ListingVisibility.ARCHIVE)
                visListing = con.prepareStatement("SELECT archivelisting(?)");
            if (visListing != null) {
                visListing.setLong(1, listing.getLID());
                visListing.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    //TODO handle exceptions better
    //TODO will need limits as database grows add in paging

    /**
     * Gets all the listings that all users have posted
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

    private static void getListingsProcessing(String sql, ArrayList<Listing> ret) throws DatabaseException {
        try (Connection con = DatabaseConnection.read.getConnection()) {
            PreparedStatement getListing = con.prepareStatement(sql);
            ResultSet rs = getListing.executeQuery();
            while (rs.next()) {
                ret.add(createListingFromSQL(rs));
            }
        } catch (SQLException e) {

        }
    }

    /**
     * This is used to set up the prepared statement for querying based of just gender. This calls the common
     * method getLIDDatabase which gets the listings ids for all listings that fit the filter
     * @param gender   The stirng value for the filter: MALE, FEMALE. COED
     * @return  The list of listing ids that fill the query
     */
    public static ArrayList<Long> GenderFilteredListing(String gender) {

        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities FROM getlistings WHERE gender = ?";
        return getLIDDatabase(sql,gender);
    }
    /**
     * This is used to set up the prepared statement for querying based of just housing. This calls the common
     * method getLIDDatabase which gets the listings ids for all listings that fit the filter
     * @param housing   The string value for the filter of housing
     * @return  The list of listing ids that fill the query
     */
    public static ArrayList<Long> HousingOptionsFilteredListing(String housing) {
        String sql = "SELECT lid, uid, `desc`, rent, address, furnished, gender, housing, payment, parking, utilities " +
                "FROM getlistings WHERE housing = ?";
        return getLIDDatabase(sql,housing);
    }

    /**
     * @param sql the prepared sql statement
     * @param value The string enum we are querying
     * @return The list of lids that fit the query
     */
    private static ArrayList<Long> getLIDDatabase(String sql,String value){
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
     * @param lid listing id that it was given at creation
     * @return listing object with that listing ID number
     */
    public static Listing GetListing(long lid) throws DatabaseException {
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

        }
        return ret;
    }

    /**
     * Gets all of the listings of the user
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

    //TODO Add in favorite stuff
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

    private static void addUserFavoriteListing(User user, Listing listing) {
        String sql = "SELECT favlisting(?,?)";
        processFavoriteSQL(user, listing, sql);
    }

    private static void removeUserFavoriteListing(User user, Listing listing) {
        String sql = "SELECT unfavlisting(?,?)";
        processFavoriteSQL(user, listing, sql);
    }

    private static void processFavoriteSQL(User user, Listing listing, String sql) {
        try {
            PreparedStatement addFavorite = DatabaseConnection.write.getConnection().prepareStatement(sql);
            addFavorite.setLong(1, user.getUID());
            addFavorite.setLong(2, listing.getLID());
            addFavorite.execute();
        } catch (SQLException e) {

        }
    }


    /**
     * Helper function to create the listings object from the sql select row
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
