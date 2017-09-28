
/**
 * Write a description of class Food here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Food extends Item
{
    private String type = "Food";
    private String name;
    
    public Food(String item) {
        name = item;
    }
    
    /**
     * 
     */
    public String getType()
    {
        return type;
    }
    
    public String getName()
    {
        return name;
    }
}
