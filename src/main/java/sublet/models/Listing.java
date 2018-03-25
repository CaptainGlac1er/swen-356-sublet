package sublet.models;

import sublet.Exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is the listing class. It is the underlying representation of the information within a listing
 * Language: Java
 * Created: 2/14/18
 * Revisited: 2/21/18
 *
 * @author Stephen Cook(sjc5897@g.rit.edu)
 * @author Molly Feldmann (mef4232@g.rit.edu)
 */
public class Listing {

    public static ArrayList<GenderOptions> genderList = new ArrayList<>(Arrays.asList(GenderOptions.values()));
    public static ArrayList<HousingTypeOptions> housingList = new ArrayList<>(Arrays.asList(HousingTypeOptions.values()));
    public static ArrayList<IsFurnishedOptions> furnishedList = new ArrayList<>(Arrays.asList(IsFurnishedOptions.values()));
    public static ArrayList<PaymentFrequencyOptions> paymentList = new ArrayList<>(Arrays.asList(PaymentFrequencyOptions.values()));
    public static ArrayList<ParkingOption> parkingList = new ArrayList<>(Arrays.asList(ParkingOption.values()));
    private long lid;
    private long user;
    private String desc;
    private String rent;
    private String address;
    private IsFurnishedOptions is_furnished;
    private GenderOptions gender;
    private HousingTypeOptions housing_type;
    private PaymentFrequencyOptions payment_frequency;
    private ParkingOption parking_type;
    private boolean utilIncluded;

    private boolean isFavorited = false;
    private ListingVisibility listing_visibility;

    public Listing() {

    }

    public Listing(User user,
                   String desc,
                   String rent,
                   String address,
                   PaymentFrequencyOptions payment_frequency,
                   GenderOptions gender,
                   HousingTypeOptions housing_type,
                   IsFurnishedOptions is_furnished,
                   ParkingOption parking_type,
                   boolean utilIncluded
    ) {
        this.user = user.getUID();
        this.desc = desc;
        this.rent = rent;
        this.address = address;
        this.payment_frequency = payment_frequency;
        this.gender = gender;
        this.housing_type = housing_type;
        this.is_furnished = is_furnished;
        this.parking_type = parking_type;
        this.utilIncluded = utilIncluded;
    }

    public static ListingVisibility getVisibilityValue(String option) {
        return ListingVisibility.valueOf(option);
    }

    public static GenderOptions getGenderValue(String option) {
        return GenderOptions.valueOf(option);
    }

    public static HousingTypeOptions getHousing_type(String option) {
        return HousingTypeOptions.valueOf(option);
    }

    public static IsFurnishedOptions getIs_furnished(String option) {
        return IsFurnishedOptions.valueOf(option);
    }

    public static PaymentFrequencyOptions getPaymentValue(String option) {
        return PaymentFrequencyOptions.valueOf(option);
    }

    public static ParkingOption getParkingValue(String option) {
        return ParkingOption.valueOf(option);
    }

    public boolean isFavorited() {
        return isFavorited;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public ListingVisibility getListingVisibility() {
        return listing_visibility;
    }

    public void setListingVisibility(ListingVisibility listing_visibility) {
        this.listing_visibility = listing_visibility;
    }

    public void setLid(long lid) {
        this.lid = lid;
    }

    public long getLID() {
        return this.lid;
    }

    public User getUser() throws DatabaseException {
        return Users.getCurrentUserUID(this.user);
    }

    public void setUser(User user) {
        this.user = user.getUID();
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRent() {
        return this.rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public PaymentFrequencyOptions getPayment_frequency() {
        return this.payment_frequency;
    }

    public GenderOptions getGender() {
        return this.gender;
    }

    public void setGender(GenderOptions gender) {
        this.gender = gender;
    }

    public HousingTypeOptions getHousingType() {
        return this.housing_type;
    }

    public void setHousingType(HousingTypeOptions housing_type) {
        this.housing_type = housing_type;
    }

    public IsFurnishedOptions getIsFurnished() {
        return this.is_furnished;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ParkingOption getParking_type() {
        return parking_type;
    }

    public void setFurnished(IsFurnishedOptions is_furnished) {
        this.is_furnished = is_furnished;
    }

    public void setPayment(PaymentFrequencyOptions payment_frequency) {
        this.payment_frequency = payment_frequency;
    }

    public void setParkingType(ParkingOption parking_type) {
        this.parking_type = parking_type;
    }

    public boolean getUtilIncluded() {
        return this.utilIncluded;
    }

    public void setUtilIncluded(boolean val) {
        this.utilIncluded = val;
    }

    public ArrayList<ListingVisibility> getVisibiltyOptions() {
        try {
            return ListingVisibility.getValidVisibility(this.getUser());
        } catch (DatabaseException e) {
        }
        return null;
    }


    /**
     * Enum used to represent payment frequency options
     */
    public enum PaymentFrequencyOptions {
        MONTHLY("Per Month"), SEMESTERLY("Per Semester"), YEARLY("Per Year");
        private String to_str;

        PaymentFrequencyOptions(String to_str) {
            this.to_str = to_str;
        }

        public String toString() {
            return to_str;
        }
    }

    /**
     * Enum used to represent gender options
     */
    public enum GenderOptions {
        COED("Co-Ed"),
        FEMALE("Female Only"),
        MALE("Male Only");
        private String to_str;

        GenderOptions(String to_str) {
            this.to_str = to_str;
        }

        public String toString() {
            return to_str;
        }
    }

    /**
     * Enum used to represent housing options
     */
    public enum HousingTypeOptions {
        COLONY("Colony Manor"), GLOBAL("Global Village"), PARKPOINT("Park Point"), PERKINS("Perkins Green"),
        PROVINCE("Province"), RACQUETCLUB("Racquet Club"), RITINN("RIT Inn"),
        RIVERKNOLL("Riverknoll"), LODGE("The Lodge"), UC("University Commons"), OFFCAMPUS("Off Campus Housing");
        private String to_str;

        HousingTypeOptions(String to_str) {
            this.to_str = to_str;
        }

        public String toString() {
            return to_str;
        }
    }

    /**
     * Enum used to represent furnished options
     */
    public enum IsFurnishedOptions {
        FURNISHED("Furnished"), UNFURNISHED("Unfurnished"), PART("Partially Furnished");
        private String to_str;

        IsFurnishedOptions(String to_str) {
            this.to_str = to_str;
        }

        public String toString() {
            return to_str;
        }
    }

    public enum ParkingOption {
        ON_STR("On Street Parking"), PVT_GARAGE("Private Garage"), PUB_GARAGE("Public Garage"),
        DRIVE("Driveway"), PUB_LOT("Public Parking Lot"), PVT_LOT("Private Parking Lot"), OTHER("Other");
        private String to_str;

        ParkingOption(String str) {
            this.to_str = str;
        }

        public String toString() {
            return to_str;
        }
    }

    public enum ListingVisibility {
        ACTIVE("Active", Roles.CurrentRoles.get("User")), ARCHIVE("Archive", Roles.CurrentRoles.get("User")), RIT("Rit", Roles.CurrentRoles.get("RIT"));
        private String name;
        private ArrayList<Role> rolesRequired;

        ListingVisibility(String name, ArrayList<Roles> roles) {
            this.name = name;
            rolesRequired = rolesRequired;
        }

        ListingVisibility(String name, Role role) {
            this.name = name;
            this.rolesRequired = new ArrayList<Role>();
            this.rolesRequired.add(role);
        }

        public static ArrayList<ListingVisibility> getValidVisibility(User user) {
            ArrayList<ListingVisibility> ret = new ArrayList<>();
            for (ListingVisibility lv : ListingVisibility.values()) {
                for (Role role : user.getUserRoles()) {
                    if (lv.rolesRequired.contains(role)) {
                        ret.add(lv);
                        break;
                    }

                }
            }
            return ret;
        }

        public String toString() {
            return name;
        }
    }
}
