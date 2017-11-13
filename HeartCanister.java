
public class HeartCanister extends HealingItem
{
    private String type;
    private String name;
    private int healthIncrease;
    
    public HeartCanister() {
        type = "HealingItem";
        name = "HeartCanister";
        healthIncrease = 25;
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
