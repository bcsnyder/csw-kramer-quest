public class Empty implements Tileable
{
    char symbol;
    String category;
    
    public Empty() {
        symbol = '.';
        category = "Empty";
    }
    
    public String getCategory() {
        return category;
    }
    
    public char getSymbol() {
        return symbol;
    }
}
