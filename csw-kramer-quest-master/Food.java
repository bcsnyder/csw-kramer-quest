
/**
 * A simple subclass of Item that is food. Used
 * to display to user the inventory screen.
 */
public class Food extends Item
{
    private String type;
    private String name;
    private int staminaIncrease;
    
    public Food() {
        type = "Food";
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
