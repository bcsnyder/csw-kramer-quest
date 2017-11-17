public class Wall implements Tileable
{
    char symbol;
    String category;
    
    public Wall(boolean locatedAtTop) {
        if (locatedAtTop == true) {
            symbol = '_';
        } else {
            symbol = '|';
        }
        category = "Wall";
    }
    
    public char getSymbol() {
        return symbol;
    }
}
