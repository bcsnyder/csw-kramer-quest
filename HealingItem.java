
/**
 * A simple subclass of Item that is for potions and such. Used
 * to display to user the inventory screen.
 */
public class HealingItem extends Item
{
    private String type;
    private String name;
    String category;
    private int healthIncrease;
    private char symbol;
    
    public HealingItem() {
        type = "HealingItem";
        category = "Item";
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
    
    public int use() {
        return healthIncrease;
    }
}
