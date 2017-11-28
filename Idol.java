
/**
 * The endgame idol that makes you win!!
 */
public class Idol extends Item implements Tileable
{
    private char symbol;
    private String type;
    private String name;
    String category;

    public Idol() {
        type = "Idol";
        name = "Idol";
        symbol = '*';
        category = "Item";
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
    
    public char getSymbol() {
        return symbol;
    }
}
