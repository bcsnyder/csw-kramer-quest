
public class Bread extends Food
{
    private char symbol;
    private String type;
    private String name;
    private int staminaIncrease;
    String category;
    
    public Bread() {
        type = "Food";
        category = "Item";
        name = "Bread";
        staminaIncrease = 15;
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
    
    public char getSymbol() {
        return symbol;
    }
}
