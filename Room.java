public class Room 
{
    int level;
    int length;
    int width;
    int numMonsters;
    char [][] map;
    
    public Room (int num, int min, int max)
    {
        level = num;
        length = (int) (Math.random() * (max - min))+ min;
        width = (int) (Math.random() * (max - min)) + min;
        numMonsters = level * 2;
        map = new char[length][width];
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
        return level;
    }
    
    public int getHeight() {
        return length;
    }
    
    public char getTile(int x, int y) {
        return map[y][x];
    }
}

