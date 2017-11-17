public class Empty implements Tileable
{
    char symbol;
    String category;
    
    public Empty() {
        symbol = '.';
        category = "Empty";
    }
    
    public char getSymbol() {
        return symbol;
    }
}
