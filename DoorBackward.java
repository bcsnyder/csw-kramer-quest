public class DoorBackward implements Tileable
{
    char symbol;
    String category;
    
    public DoorBackward() {
        symbol = '^';
        category = "Door Back";
    }
    
    public String getCategory() {
        return category;
    }
    
    public char getSymbol() {
        return symbol;
    }
}