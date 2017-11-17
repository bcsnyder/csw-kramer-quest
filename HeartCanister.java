
public class HeartCanister extends HealingItem
{
    private String type;
    private String name;
    private int healthIncrease;
    private String category;
    
    public HeartCanister() {
        type = "HealingItem";
        category = "Item";
        name = "HeartCanister";
        healthIncrease = 25;
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
