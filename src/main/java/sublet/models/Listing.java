package sublet.models;

import java.util.List;
import static java.util.Arrays.asList;


public class Listing {
    private String user;
    private String desc;
    private String rent;
    private List<String> pick_gender = asList("Coed", "Female only", "Male only");
    private String gender;
    private List<String> pick_housing_type = asList("Colony Manor", "Global Village", "Park Point", "Perkins Green",
            "Province", "Racquet Club", "RIT Inn & Conference Center", "Riverknoll", "The Lodge", "University Commons",
            "Off campus");
    private String housing_type;
    private List<String> pick_is_furnished = asList("Furnished", "Unfurnished", "Partially furnished");
    private String is_furnished;


    public Listing(String user, String desc, String rent, String gender, String housing_type, String is_furnished){
        this.user = user;
        this.desc = desc;
        this.rent = rent;
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
    public String getGender(){
        return this.gender;
    }
    public String getHousingType(){
        return this.housing_type;
    }
    public String getIsFurnished(){
        return this.is_furnished;
    }
}
