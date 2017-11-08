
public class Bread extends Food
{
    private char symbol;
    private String type;
    private String name;
    private int staminaIncrease;
    
    public Bread() {
        type = "Food";
        name = "Bread";
        staminaIncrease = 10;
        symbol = '$';
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
