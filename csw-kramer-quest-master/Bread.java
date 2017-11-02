
public class Bread extends Food
{
    private String type;
    private String name;
    private int staminaIncrease;
    
    public Bread() {
        type = "Food";
        name = "Bread";
        staminaIncrease = 10;
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
