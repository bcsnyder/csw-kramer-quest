
/**
 * A simple subclass of Item that is food. Used
 * to display to user the inventory screen.
 */
public class Food extends Item
{
    private String type = "Food";
    private String name;
    
    public Food(String item) {
        name = item;
    }
    
    /**
     * getter
     */
    public String getType()
    {
        return type;
    }
    
    public String getName()
    {
        return name;
    }
}
