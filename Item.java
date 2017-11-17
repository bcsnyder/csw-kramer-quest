
/**
 * Just an abstract class with basic info about the item it holds.
 */
public abstract class Item implements Tileable
{
    private char symbol;
    private String type;
    private String name;
    String category;

    public String getType()
    {
        return type;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol() {
        return symbol;
    }
}
