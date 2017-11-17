
public class DoorForward implements Tileable
{
    char symbol;
    String category;
    
    public DoorForward() {
        symbol = '#';
        category = "Door Forward";
    }
    
    public String getCategory() {
        return category;
    }
    
    public char getSymbol() {
        return symbol;
    }
}
