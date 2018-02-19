package sublet.models;

import java.util.List;
import static java.util.Arrays.asList;

import java.util.List;

public class Listing {

    private String user;
    private String desc;
    private String rent;
    public enum PaymentFrequencyOptions{ MONTHLY, SEMESTERLY }
    PaymentFrequencyOptions payment_frequency;
    public enum GenderOptions{ COED, FEMALE, MALE }
    GenderOptions gender;
    public enum HousingTypeOptions{ COLONYMANOR, GLOBALVILLAGE, PARKPOINT, PERKINSGREEN, PROVINCE, RACQUETCLUB, RITINN,
        RIVERKNOLL, LODGE, UNIVERSITYCOMMONS, OFFCAMPUS }
    HousingTypeOptions housing_type;
    public enum IsFurnishedOptions{ FURNISHED, UNFURNISHED, PARTIALLYFURNISHED }
    IsFurnishedOptions is_furnished;

     private List<String> pick_gender = asList("Coed", "Female only", "Male only");
    //private String gender;
    private List<String> pick_housing_type = asList("Colony Manor", "Global Village", "Park Point", "Perkins Green",
            "Province", "Racquet Club", "RIT Inn & Conference Center", "Riverknoll", "The Lodge", "University Commons",
            "Off campus");
    //private String housing_type;
    private List<String> pick_is_furnished = asList("Furnished", "Unfurnished", "Partially furnished");
    //private String is_furnished;


    public Listing(String user,
                   String desc,
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
