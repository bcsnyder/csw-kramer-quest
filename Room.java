public class Room 
{
    private int roomNumber;
    private int length;
    private int width;
    private int numMonsters;
    private char [][] map;
    private int roomNum;
    private int doorWall;
    private int level; 
    
    public Room (int num, int min, int max)
    {
        roomNumber = num;
        length = (int) (Math.random() * (max - min))+ min;
        width = (int) (Math.random() * (max - min)) + min;
        //This allows the number of monsters to go up each level, only 10 levels but can add more
        if (roomNumber <= 3){
            max = 4;
            min = 1;
        } else if (roomNumber > 3 && roomNumber <= 6   ){
            max = 6;
            min = 1;
        }else if (roomNumber > 6 && roomNumber <= 10){
            max = 8;
            min = 1;
        }
        numMonsters = (int) Math.floor(Math.random() * (max - min)) + min;
        map = new char[length][width];
    }
    
    public void fill() {
        fillDots();
        fillWalls();
        fillSymbols();
    }
    
    public String toString() {
        String output = "";
        for (int counter1 = 0; counter1 < length; counter1++)
        {
            for (int counter2 = 0; counter2 < width; counter2++)
            {
                output += " " + map[counter1][counter2] + " ";
            }
        }
        return output;
    }
   
    public void fillDots()
    {
        for (int counter1 = 0; counter1 < length; counter1++)
            for (int counter2 = 0; counter2 < width; counter2++)
                map[counter1][counter2] = '.';
    }
    
    public void addPlayer(int x, int y) {
        map[y][x] = '@';
    }
    
    public void removePlayer() {
        for (int counter1 = 0; counter1 < length; counter1++) {
            for (int counter2 = 0; counter2 < width; counter2++) {
                if(map[counter1][counter2] == '@') {
                    map[counter1][counter2] = '.';
                    return;
                }
            }
        }
    }
    
    public void fillWalls()
    {
        for (int counter1 = 0; counter1 < length; counter1++)
        {
            for (int counter2 = 0; counter2 < width; counter2++)
            {
               if (counter1 == 0 || counter1 == length - 1)
               {  
                   map[counter1][counter2] = '-';
               }
               else if (counter2 == 0 || counter2 == width - 1)
               {
                   map[counter1][counter2] = '|';
               }
            }
        }
    }
    
    public void fillSymbols()
    {
        /* Monster */
        for (int counter = 0; counter < numMonsters; counter++)
        {
            int num1 = (int) Math.floor(Math.random() * (length - 2)) + 1;
            int num2 = (int) Math.floor(Math.random() * (width - 2)) + 1;
            map[num1][num2] = 'G'; //check if placing on top of a monster
        }
        
        /* Door */
        int xCoor = 0;      
        int yCoor = 0;
        int rand1 = (int) (Math.random() * 4) + 1; 
        doorWall = rand1; 
        if (rand1 == 1 || rand1 == 3)
        {
             xCoor = (int) (Math.random () * (width - 2)) + 1;
        }
        if (rand1 == 2 || rand1 == 4) {
             yCoor = (int) (Math.random () * (length - 2)) + 1;
        }
        if (rand1 == 1) {
             yCoor = 0; 
        }
        if (rand1 == 2) {
             xCoor= width - 1;
        }
        if (rand1 == 3) {
             yCoor = length - 1;
        }
        if (rand1 == 4) {
             xCoor = 0; 
        }
        
        map [yCoor][xCoor] = '#'; 
        
        /* Treasure */
        int num1 = (int) Math.floor(Math.random() * (length - 2)) + 1;
        int num2 = (int) Math.floor(Math.random() * (width - 2)) + 1;
        map[num1][num2] = '$'; 
    }
    
    public int getLevel() {
        return roomNumber;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return length;
    }
    
    public char getTile(int x, int y) {
        return map[y][x];
    }
     
    public int returnDoorWall(){
        return doorWall;
    }
    
    public void addBackDoor (int w, int y, int x){
        //This allows there to be a door back but i still have to add the ability to go back
        int yCoor = y;
        int xCoor = x; 
        if (w == 1) {
             xCoor = 0; 
        }
        if (w == 2) {
             yCoor= width - 1;
        }
        if (w == 3) {
             xCoor = length - 1;
        }
        if (w == 4) {
             yCoor = 0; 
        }
        
            map [xCoor][yCoor] = '^';
        
        }
    }

