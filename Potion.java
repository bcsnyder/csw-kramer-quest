
public class Potion extends HealingItem
{
    private String type;
    private String name;
    private int healthIncrease;
    
    public Potion() {
        type = "HealingItem";
        name = "Potion";
        healthIncrease = 10;
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
