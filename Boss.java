import java.util.ArrayList;
public class Boss extends Monster implements Tileable
{
    String name;
    char symbol;
    int health; //NO need to have doubles
    int atk;
    private boolean moved;
    String category;

    public Boss() {
        symbol = 'B';
        name = "Boss";
        category = "Monster";
        health = (int)(Math.random()*20 + 80);
        atk = (int)(Math.random()*2 + 24);
    }

    public String getCategory() {
        return category;
    }
    
    /**
     * When called, this method will subtract the appropriate amount of health from the monster.  If the monster
     * is alive, it returns true.  If the monster is dead it returns false.
     * 
     */

    public boolean ouchie(int y)
    {
        health = health - y;
        if (health <= 0){
            return false;
        }else{
            return true;
        }
    }
    
    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean hasMoved) {
        moved = hasMoved;
    }

    public Room chooseMove(Room tiles) {
        int newX;
        int newY;
        Room getPlayer = tiles;
        Player currentPlayer = getPlayer.removePlayer();
        boolean outOfMoves = false;
        ArrayList directions = new ArrayList();

        for(int i = 1; i<=4; i++) {
            directions.add(i);
        }
        
        int directionChooser = 0;
        int chosenDirection = 0;
        
        if (x < currentPlayer.getX()) {
            chosenDirection = 4;
        } else if (y < currentPlayer.getY()) {
            chosenDirection = 2;
        } else if (x > currentPlayer.getX()) {
            chosenDirection = 3;
        } else {
            chosenDirection = 1;
        }
        
        do {
            newX = x;
            newY = y;

            if(directions.isEmpty()) {
                outOfMoves = true;
                break;
            }

            if (chosenDirection == 1) {
                newY = y - 1;
                directions.remove(directions.indexOf(1));
            } else if(chosenDirection == 2) {
                newY = y + 1;
                directions.remove(directions.indexOf(2));
            } else if(chosenDirection == 3) {
                newX = x - 1;
                directions.remove(directions.indexOf(3));
            } else {
                newX = x + 1;
                directions.remove(directions.indexOf(4));
            }
            if (tiles.getTile(newX,newY) == '@') {
                return null;
            }
            
            directionChooser = (int)(Math.random()*directions.size());
            chosenDirection = (int)directions.get(directionChooser);
        } while (tiles.getTile(newX,newY) != '.');

        if(!outOfMoves) {
            Tileable thisMonster = tiles.removeTile(x,y);
            x = newX;
            y = newY;
            tiles.addTile(x,y,thisMonster);
        }
        
        moved = true;
        
        return tiles;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return health;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getAttack() {
        return atk;
    }
}