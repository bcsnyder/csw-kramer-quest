
/**
 * A simple subclass of Item that is food. Used
 * to display to user the inventory screen.
 */
public class Food extends Item
{
    private char symbol;
    private String type;
    private String name;
    private int staminaIncrease;
    String category;

    public Food() {
        type = "Food";
        symbol = '$';
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
