package sublet.objects;

public class Item {
    private String Name;
    private String Description;
    public  Item (String name, String description){
        this.Name = name;
        this.Description = description;
    }
    public String getName(){
        return Name;
    }
    public void setName(String name){
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
