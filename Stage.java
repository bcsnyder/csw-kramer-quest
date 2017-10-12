public class Stage 
{
    private int size;
    private Room[] floor;
    
    public Stage(int numRooms)
    {
        size = numRooms;
        floor = new Room[numRooms];
        makeRooms();
    }
    
    public void makeRooms() 
    {
        for (int counter = 0; counter < size; counter++)
        {
            int min = 7 + counter/2;
            int max = 11 + counter/2;
            Room add = new Room(counter, min, max);
            add.fill();
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
    
    public Room getRoom(int num) {
        return floor[num];
    }
    
    //floor [0] = one;  
    //floor [1] = two;
    //floor [2] = three;
    //floor [3] = four;
}
