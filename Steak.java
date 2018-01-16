
public class Steak extends Food
{
    private char symbol;
    private String type;
    private String name;
    private int staminaIncrease;
    String category;
    
    public Steak() {
        type = "Food";
        category = "Item";
        int rng = (int) (Math.random() * 100);
        if (rng <= 94) {
          name = "Steak";
         } else {
          name = "FSteak"   ;
         }
        staminaIncrease = 25;
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
