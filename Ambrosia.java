
public class Ambrosia extends Food
{
    private String type;
    private String name;
    private int staminaIncrease;
    private String category;
    
    public Ambrosia() {
        type = "Food";
        name = "Ambrosia";
        staminaIncrease = 100;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getType()
    {
        return type;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int eat() {
        return staminaIncrease;
    }
}
