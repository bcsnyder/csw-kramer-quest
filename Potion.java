
public class Potion extends HealingItem
{
    private String type;
    private String name;
    private String category;
    private int healthIncrease;
    
    public Potion() {
        type = "HealingItem";
        name = "Potion";
        category = "Item";
        healthIncrease = 10;
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
