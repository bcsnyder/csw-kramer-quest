/**
 * A simple subclass of Item that is a rare key. Used
 * to display to user the inventory screen.
 */
public class BigKey extends Item
{
    private char symbol;
    private String type;
    private String name;
    private boolean use = false;
    String category;

    public BigKey() {
        type = "Key";
        category = "Item";
        symbol = '$';
        name = "Big Key";
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
