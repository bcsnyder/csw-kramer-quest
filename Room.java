public class Room 
{
    private int roomNumber;
    private int length;
    private int width;
    private int numMonsters;
    private Tileable [][] map;
    private int roomNum;
    private int doorWall;
    private int level; 
    private int bYcoor = 0; //holds the variables for the back doors y coor
    private int bXcoor = 0; //holds the variables for the back doors x coor

    public Room (int num, int min, int max)
    {
        roomNumber = num;
        length = (int) (Math.random() * (max - min))+ min;
        width = (int) (Math.random() * (max - min)) + min;
        //This allows the number of monsters to go up each level, only 10 levels but can add more
        if (roomNumber <= 3){
            max = 2;
            min = 1;
        } else if (roomNumber > 3 && roomNumber <= 10){
            max = 4;
            min = 1;
        }else if (roomNumber > 10 && roomNumber <= 21){
            max = 5;
            min = 2;
        }
        numMonsters = (int) Math.floor(Math.random() * (max - min)) + min;
        map = new Tileable[length][width];
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
                output += " " + map[counter1][counter2].getSymbol() + " ";
            }
        }
        return output;
    }

    public void fillDots()
    {
        for (int counter1 = 0; counter1 < length; counter1++)
            for (int counter2 = 0; counter2 < width; counter2++)
                map[counter1][counter2] = new Empty();
    }

    public void addTile(int x, int y, Tileable t) {
        map[y][x] = t;
    }

    public Tileable removeTile(int x, int y) {
        Tileable deletedTile = map[y][x];
        map[y][x] = new Empty();
        return deletedTile;
    }
    
    public Player removePlayer() {
        for (int counter1 = 0; counter1 < length; counter1++) {
            for (int counter2 = 0; counter2 < width; counter2++) {
                if(map[counter1][counter2].getSymbol() == '@') {
                    Player removedPlayer = (Player)map[counter1][counter2];
                    map[counter1][counter2] = new Empty();
                    return removedPlayer;
                }
            }
        }
        return null;
    }

    public void fillWalls()
    {
        for (int counter1 = 0; counter1 < length; counter1++)
        {
            for (int counter2 = 0; counter2 < width; counter2++)
            {
                if (counter1 == 0 || counter1 == length - 1)
                {  
                    map[counter1][counter2] = new Wall(true);
                }
                else if (counter2 == 0 || counter2 == width - 1)
                {
                    map[counter1][counter2] = new Wall(false);
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

            int monsterType = 0;
            if (roomNumber < 10) {
                monsterType = (int)(Math.random() * 2);
            } else {
                monsterType = (int)(Math.random() * 2);
            }

            Monster[] allMonsterTypes = setPossibleMonsters();
            Monster newMonster = allMonsterTypes[monsterType];
            newMonster.setPos(num2, num1);
            map[num1][num2] = newMonster; //check if placing on top of a monster
        }

        /* Door */
        int xCoor = 0;      
        int yCoor = 0;
        int rand1 = (int) (Math.random() * 4) + 1; 
        doorWall = rand1; 
        if (rand1 == 1 || rand1 == 3)
        {
            xCoor = (int) (Math.random () * (width - 2) + 1);
        }
        if (rand1 == 2 || rand1 == 4) {
            yCoor = (int) (Math.random () * (length - 2) + 1);
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

        map [yCoor][xCoor] = new DoorForward(); 

        /* Treasure */
        int num1 = (int) Math.floor(Math.random() * (length - 2)) + 1;
        int num2 = (int) Math.floor(Math.random() * (width - 2)) + 1;
        map[num1][num2] = new Bread(); 
    }

    private Monster[] setPossibleMonsters() {
        Monster[] possibleMonsters = new Monster[5];
        possibleMonsters[0] = new Gremlin();
        possibleMonsters[1] = new Skeleton();
        possibleMonsters[2] = new Troll();
        possibleMonsters[3] = new Dragon();
        possibleMonsters[4] = new Changeling();

        return possibleMonsters;
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
        return map[y][x].getSymbol();
    }
    
    public Tileable returnTileObject(int x, int y) {
        return map[y][x];
    }
    
    public int returnDoorWall(){
        return doorWall;
    }

    public void addBackDoor (int w, int y, int x){
        //This allows there to be a door back but i still have to add the ability to go back
        int yCoor = y;
        int xCoor = x; 
        if (x == 0){
            x++;
        }else if (x == length){
            x--;
        }
        if (y == 0){
            y++;
        }else if ( y == width){
            y --;
        }
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

        map [xCoor][yCoor] = new DoorBackward();
        if (w == 1) {
            xCoor = 1;
            yCoor = y;
        }
        if (w == 2) {
            yCoor = yCoor - 1;
            xCoor = x;
        }
        if (w == 3) {
            xCoor = xCoor - 1;
            yCoor = y;
        }
        if (w == 4) {
            yCoor = 1; 
            xCoor = x; 
        }
        bXcoor = yCoor;
        bYcoor = xCoor;//I dont know why this is backwards. But it is

    }

    public int returnPositionX (){
        return bXcoor;
    }

    public int returnPositionY(){
        return bYcoor; 
    }
}

