public class Wall implements Tileable
{
    char symbol;
    
    public Wall(boolean locatedAtTop) {
        if (locatedAtTop == true) {
            symbol = '_';
        } else {
            symbol = '|';
        }
    }
    
    public char getSymbol() {
        return symbol;
    }
}
