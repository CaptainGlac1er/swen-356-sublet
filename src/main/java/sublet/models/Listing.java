package sublet.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is the listing class. It is the underlying representation of the information within a listing
 * Language: Java
 * Created: 2/14/18
 * Revisited: 2/21/18
 * @author Stephen Cook(sjc5897@g.rit.edu)
 * @author Molly Feldmann (mef4232@g.rit.edu)
 */
public class Listing {

    /**
     * Enum used to represent payment frequency options
     */
    public enum PaymentFrequencyOptions{
        MONTHLY ("Per Month"), SEMESTERLY ("Per Semester"), YEARLY ("Per Year");
        private String to_str;
        PaymentFrequencyOptions(String to_str){
            this.to_str = to_str;
        }
        public String toString(){
            return to_str;
        }
    }
    /**
     * Enum used to represent gender options
     */
    public enum GenderOptions{
        COED("Co-Ed"),
        FEMALE ("Female Only"),
        MALE ("Male Only");
        private String to_str;
        GenderOptions(String to_str){
            this.to_str = to_str;
        }
        public String toString(){
            return to_str;
        }
    }
    /**
     * Enum used to represent housing options
     */
    public enum HousingTypeOptions {
        COLONY ("Colony Manor"), GLOBAL("Global Village"), PARKPOINT ("Park Point"), PERKINS ("Perkins Green"),
        PROVINCE ("Province"), RACQUETCLUB ("Racquet Club"), RITINN ("RIT Inn"),
        RIVERKNOLL ("Riverknoll"), LODGE ("The Lodge"), UC ("University Commons"), OFFCAMPUS ("Off Campus Housing");
        private String to_str;
        HousingTypeOptions(String to_str){
            this.to_str = to_str;
        }
        public String toString(){
            return to_str;
        }
    }
    /**
     * Enum used to represent furnished options
     */
    public enum IsFurnishedOptions{
        FURNISHED ("Furnished"), UNFURNISHED("Unfurnished"), PART ("Partially Furnished");
        private String to_str;
        IsFurnishedOptions(String to_str){
            this.to_str = to_str;
        }
        public String toString(){
            return to_str;
        }
    }

    public enum ParkingOption{
        ON_STR ("On Street Parking"), PVT_GARAGE("Private Garage"), PUB_GARAGE("Public Garage"),
        DRIVE("Driveway"), PUB_LOT("Public Parking Lot"),PVT_LOT ("Private Parking Lot"), OTHER("Other");
        private String to_str;
        ParkingOption(String str){this.to_str = to_str;}
        public String toString(){return to_str;}
    }

    private long lid;
    private User user;
    private String desc;
    private String rent;
    private String address;
    private IsFurnishedOptions is_furnished;
    private GenderOptions gender;
    private HousingTypeOptions housing_type;
    private PaymentFrequencyOptions payment_frequency;
    private ParkingOption parking_type;
    private HashMap<String, Tag> properties;

    public Listing(User user,
                    String desc,
                    String rent,
                    String address,
                    PaymentFrequencyOptions payment_frequency,
                    GenderOptions gender,
                    HousingTypeOptions housing_type,
                    IsFurnishedOptions is_furnished, ParkingOption parking_type
    ){
        this.user = user;
        this.desc = desc;
        this.rent = rent;
        this.address = address;
        this.payment_frequency = payment_frequency;
        this.gender = gender;
        this.housing_type = housing_type;
        this.is_furnished = is_furnished;
        this.parking_type = parking_type;
        this.properties = new HashMap<>();
    }
    public Listing(long lid,
                   User user,
                   String desc,
                   String rent,
                   String address,
                   PaymentFrequencyOptions payment_frequency,
                   GenderOptions gender,
                   HousingTypeOptions housing_type,
                   IsFurnishedOptions is_furnished,
                   ParkingOption parking_type
    ){
        this.lid = lid;
        this.user = user;
        this.desc = desc;
        this.rent = rent;
        this.address = address;
        this.payment_frequency = payment_frequency;
        this.gender = gender;
        this.housing_type = housing_type;
        this.is_furnished = is_furnished;
        this.parking_type = parking_type;
        this.properties = new HashMap<>();
    }


    public static ArrayList<GenderOptions> genderList = new ArrayList<>(Arrays.asList(GenderOptions.values()));
    public static ArrayList<HousingTypeOptions> housingList = new ArrayList<>(Arrays.asList(HousingTypeOptions.values()));
    public static ArrayList<IsFurnishedOptions> furnishedList = new ArrayList<>(Arrays.asList(IsFurnishedOptions.values()));
    public static ArrayList<PaymentFrequencyOptions> paymentList = new ArrayList<>(Arrays.asList(PaymentFrequencyOptions.values()));
    public static ArrayList<ParkingOption> parkingList = new ArrayList<>(Arrays.asList(ParkingOption.values()));

    public long getLID(){return this.lid;}
    public User getUser(){
        return this.user;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getRent(){
        return this.rent;
    }
    public PaymentFrequencyOptions getPayment_frequency() {
        return this.payment_frequency;
    }
    public GenderOptions getGender(){
        return this.gender;
    }
    public HousingTypeOptions getHousingType(){
        return this.housing_type;
    }
    public IsFurnishedOptions getIsFurnished(){
        return this.is_furnished;
    }
    public String getAddress(){return this.address;}

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public void setFurnished(IsFurnishedOptions is_furnished) {
        this.is_furnished = is_furnished;
    }

    public void setGender(GenderOptions gender) {
        this.gender = gender;
    }

    public void setHousingType(HousingTypeOptions housing_type) {
        this.housing_type = housing_type;
    }

    public void setPayment(PaymentFrequencyOptions payment_frequency) {
        this.payment_frequency = payment_frequency;
    }

    public void setAddress(String address){this.address = address;}

    public void addTag(Tag property){
        this.properties.put(property.getName(), property);
    }
    public void removeTag(String propertyName){
        this.properties.remove(propertyName);
    }
    public Tag getTag(String property){
        return this.properties.get(property);
    }
    public boolean hasTag(String property){
        return this.properties.containsKey(property);
    }
    public ArrayList<Tag> getTags(){
        return new ArrayList<Tag>(this.properties.values());
    }



    public static GenderOptions getGenderValue(String option){return GenderOptions.valueOf(option);}
    public static HousingTypeOptions getHousing_type(String option){return HousingTypeOptions.valueOf(option);}
    public static IsFurnishedOptions getIs_furnished(String option){return IsFurnishedOptions.valueOf(option);}
    public static PaymentFrequencyOptions getPaymentValue(String option){return PaymentFrequencyOptions.valueOf(option);}
    public static ParkingOption getParkingValue(String option){return ParkingOption.valueOf(option);}
}
