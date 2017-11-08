
/**
 * Just an abstract class with basic info about the item it holds.
 */
public abstract class Item implements Tileable
{
    private char symbol;
    private String type;
    private String name;

    public String getType()
    {
        return type;
    }
    
    public String getName()
    {
        return name;
    }
    
    public char getSymbol() {
        return symbol;
    }
}
