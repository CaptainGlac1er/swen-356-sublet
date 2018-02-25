package sublet.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Arrays.asList;

import java.util.List;

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
        public String getTo_str(){
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
        public String getTo_str(){
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
        public String getTo_str(){
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
        public String getTo_str(){
            return to_str;
        }
    }

    public static ArrayList<GenderOptions> genderLst = new ArrayList<>(Arrays.asList(GenderOptions.values()));
    public static ArrayList<HousingTypeOptions> housingLst = new ArrayList<>(Arrays.asList(HousingTypeOptions.values()));
    public static ArrayList<IsFurnishedOptions>  furnishedLst = new ArrayList<>(Arrays.asList(IsFurnishedOptions.values()));
    public static ArrayList<PaymentFrequencyOptions> paymentLst = new ArrayList<>(Arrays.asList(PaymentFrequencyOptions.values()));

    private User user;
    private String desc;
    private String rent;
    private IsFurnishedOptions is_furnished;
    private GenderOptions gender;
    private HousingTypeOptions housing_type;
    private PaymentFrequencyOptions payment_frequency;

    public Listing(User user,String desc,
                   String rent,
                   PaymentFrequencyOptions payment_frequency,
                   GenderOptions gender,
                   HousingTypeOptions housing_type,
                   IsFurnishedOptions is_furnished
 ){
        this.user = user;
        this.desc = desc;
        this.rent = rent;
        this.payment_frequency = payment_frequency;
        this.gender = gender;
        this.housing_type = housing_type;
        this.is_furnished = is_furnished;
    }
    public static GenderOptions selectGender(String option){
        switch(option){
            case "FEMALE":
                return GenderOptions.FEMALE;
            case "MALE":
                return GenderOptions.MALE;
            case "COED":
                return GenderOptions.COED;
            default:
                return GenderOptions.MALE;
        }
    }

    public static HousingTypeOptions selectHousing(String option){
        for(HousingTypeOptions a : housingLst ){
            if(a.getTo_str().equals(option)){
                return a;
            }
        }
        return HousingTypeOptions.PARKPOINT;
    }
    public static IsFurnishedOptions selectFurnished(String option){
        for(IsFurnishedOptions a : furnishedLst ){
            if(a.getTo_str().equals(option)){
                return a;
            }
        }
        return IsFurnishedOptions.FURNISHED;
    }
    public static PaymentFrequencyOptions selectPayment(String option){
        for(PaymentFrequencyOptions a : paymentLst ){
            if(a.getTo_str().equals(option)){
                return a;
            }
        }
        return PaymentFrequencyOptions.SEMESTERLY;
    }

    public String getUser(){
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
}
