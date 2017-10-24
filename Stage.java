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
            
            //This adds the back door so that the player can go through this door
            int height = add.getHeight();
            int width = add.getWidth();
            if (counter != 0){
                if (doorWallLast == 1 || doorWallLast == 3){
                    yCoor = (int) (Math.random () * (width - 2)) + 2;
                }else if (doorWallLast == 2 || doorWallLast == 4){
                    xCoor = (int) (Math.random () * (height - 2)) + 2;
                }
                add.addBackDoor (doorWallLast,yCoor, xCoor);
            }
            System.out.println (doorWallLast);
            doorWallLast = add.returnDoorWall();
            if (doorWallLast == 1){
                doorWallLast = 3;
            }else if (doorWallLast == 2){
                doorWallLast = 4;
            }else if (doorWallLast == 3){
                doorWallLast = 1;
            }else if (doorWallLast == 4){
                doorWallLast = 2;
            }
            System.out.println (doorWallLast);
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
    
    public Room nextRoom (int roomNum){
         return floor[roomNum];
    }
    
    //floor [0] = one;  
    //floor [1] = two;
    //floor [2] = three;
    //floor [3] = four;
}

