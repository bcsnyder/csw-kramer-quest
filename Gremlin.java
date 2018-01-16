import java.util.ArrayList;
public class Gremlin extends Monster implements Tileable
{
    private String name;
    private char symbol;
    private int health; //NO need to have doubles
    private int atk;
    private int x;
    private int y;
    private boolean moved;
    String category;
    int exp = 2;
    public Gremlin() {
        symbol = 'G';
       int rng = (int) (Math.random() * 100);
        if (rng < 98 ) {
          name = "Gremlin";
       } else if (rng > 98) {
          name = "Gizmo";
        } else {
          name = "AMC";
        }
        category = "Monster";
        health = (int)(Math.random()*5 + 10);
        atk = (int)(Math.random()*2 + 1);
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
        if (health < 0) {
            health = 0;
        }
        if (health <= 0){
            return false;
        }else{
            return true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int xPos, int yPos) {
        x = xPos;
        y = yPos;
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
        boolean outOfMoves = false;
        ArrayList directions = new ArrayList();

        for(int i = 1; i<=4; i++) {
            directions.add(i);
        }

        do {
            int directionChooser = (int)(Math.random()*directions.size());
            newX = x;
            newY = y;

            if(directions.isEmpty()) {
                outOfMoves = true;
                break;
            }

            int chosenDirection = (int)directions.get(directionChooser);

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
    
    public int getExp() {
        return exp;
    }
}
