/**
 * A simple subclass of Item that is a key. Used
 * to display to user the inventory screen.
 */
public class SmallKey extends Item
{
    private char symbol;
    private String type;
    private String name;
    private boolean use = false;
    String category;

    public SmallKey() {
        type = "Key";
        category = "Item";
        symbol = '$';
        name = "Small Key";
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

    public boolean unlock() {
        use = true;
        return use;
    }
    
     public char getSymbol() {
        return symbol;
    }
}
