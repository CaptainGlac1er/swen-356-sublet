package sublet.models;

public class Listing {
    private String user;
    private String desc;
    private String rent;

    public Listing(String user, String desc, String rent){
        this.user = user;
        this.desc = desc;
        this.rent = rent;
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
}
