public class Stage 
{
    private int min;
    private int max;
    private int numRooms;
    private Room[] floor;
    private int xCoor = 0;      
    private int yCoor = 0;
    private int doorWallLast = 0; 

    public Stage(int num)
    {
        numRooms = num;
        min = 6; //can only be so small 
        max = 12;//can only be soo big, it needs to fit on screen
        floor = new Room[numRooms];
        makeRooms();
    }

    public void makeRooms() 
    {
        for (int counter = 0; counter < numRooms; counter++)
        {
            Room add = new Room(counter, min, max);
            int height = add.getHeight();
            int width = add.getWidth();

            add.fillDots();
            add.fillSymbols();

            if (counter != 0){

                if (doorWallLast == 1){
                    doorWallLast = 3;
                }else if (doorWallLast == 2){
                    doorWallLast = 4;
                }else if (doorWallLast == 3){
                    doorWallLast = 1;
                }else if (doorWallLast == 4){
                    doorWallLast = 2;
                }

                int xCoord = 0;
                int yCoord = 0;

                if (doorWallLast == 1) {
                    yCoord = 0; 
                }
                if (doorWallLast == 2) {
                    xCoord = width - 1;
                }
                if (doorWallLast == 3) {
                    yCoord = height - 1;
                }
                if (doorWallLast == 4) {
                    xCoord = 0; 
                }

                do {
                    if (doorWallLast == 1 || doorWallLast == 3){
                        xCoord = (int) (Math.random() * (width - 2)) + 1;
                    } else if (doorWallLast == 2 || doorWallLast == 4){
                        yCoord = (int) (Math.random() * (height - 2)) + 1;
                    }
                } while (add.getTile(xCoord,yCoord) == '#');

                add.addBackDoor(doorWallLast, xCoord, yCoord);
            }
            doorWallLast = add.returnDoorWall();
            floor[counter] = add;
        }

        //This adds the back door so that the player can go through this 

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

