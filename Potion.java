
public class Potion extends HealingItem
{
    private String type;
    private String name;
    private String category;
    private int healthIncrease;
    private char symbol;
    
    public Potion() {
        type = "HealingItem";
        name = "Potion";
        category = "Item";
        healthIncrease = 10;
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
    
    public char getSymbol() {
        return symbol;
    }
}
