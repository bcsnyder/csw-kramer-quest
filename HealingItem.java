
/**
 * A simple subclass of Item that is for potions and such. Used
 * to display to user the inventory screen.
 */
public class HealingItem extends Item
{
    private String type;
    private String name;
    private int healthIncrease;
    
    public HealingItem() {
        type = "HealingItem";
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
