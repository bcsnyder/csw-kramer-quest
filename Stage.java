public class Stage 
{
    private int level;
    private int min;
    private int max;
    private int numRooms;
    private Room[] floor;
    
    public Stage(int num)
    {
        level = num;
         min = (level * 2) + 6;
 +       max = (level * 4) + 6;
        numRooms = (level * 2) + 2;
        floor = new Room[numRooms];
    }
    
    public void makeRooms() 
    {
        for (int counter = 0; counter < numRooms; counter++)
        {
            Room add = new Room(level, min, max);
            add.fillDots();
            add.fillWalls();
            add.fillSymbols();
            floor[counter] = add;
        }
    }
   
    public String toString() 
    {
        String output = "";
        for (int counter = 0; counter < floor.length; counter++)
        {
            output += floor[counter].toString() + "\n\n";
        }
        return output;
    }
    
    //floor [0] = one;  
    //floor [1] = two;
    //floor [2] = three;
    //floor [3] = four;
}
