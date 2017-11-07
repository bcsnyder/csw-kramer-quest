public class Stage 
{
    private int level;
    private int min;
    private int max;
    private int numRooms;
    private Room[] floor;
    private int xCoor = 0;      
    private int yCoor = 0;
    private int doorWallLast = 0; 
    
    public Stage(int num)
    {
        level = num;
        min = 6; //can only be so small 
        max = 12;//can only be soo big, it needs to fit on screen
        numRooms = (level * 2) + 2;
        floor = new Room[numRooms];
        makeRooms();
    }
    
    public void makeRooms() 
    {
        for (int counter = 0; counter < numRooms; counter++)
        {
            Room add = new Room(counter, min, max);
            add.fillDots();
            add.fillWalls();
            add.fillSymbols();
            
            //This adds the back door so that the player can go through this 
            int height = add.getHeight();
            int width = add.getWidth();
            if (counter != 0){
                
                if (doorWallLast == 1 || doorWallLast == 3){
                    yCoor = (int) (Math.random () * (width - 2)) + 2;
                } else if (doorWallLast == 2 || doorWallLast == 4){
                    xCoor = (int) (Math.random () * (height - 2)) + 2;
                }
                
                 if (doorWallLast == 1){
                     doorWallLast = 3;
                }else if (doorWallLast == 2){
                    doorWallLast = 4;
                }else if (doorWallLast == 3){
                    doorWallLast = 1;
                }else if (doorWallLast == 4){
                    doorWallLast = 2;
                }
                
                add.addBackDoor(doorWallLast, yCoor, xCoor);
            }
            
           
            doorWallLast = add.returnDoorWall();
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
    
    public void setRoom(Room newRoom, int num) {
        floor[num] = newRoom;
    }
    
    public Room nextRoom (int roomNum){
         return floor[roomNum];
    }
     
    //floor [0] = one;  
    //floor [1] = two;
    //floor [2] = three;
    //floor [3] = four;
}
