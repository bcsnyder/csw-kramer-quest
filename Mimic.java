import java.util.ArrayList;
public class Mimic extends Monster implements Tileable
{
    String name;
    char symbol;
    int health; //NO need to have doubles
    int atk;
    private boolean moved;
    String category;

    public Mimic() {
        symbol = '$';
        name = "Mimic";
        category = "Monster";
        health = (int)(Math.random()*101)+1;
        atk = (int)(Math.random()*31)+1;
    }

    public String getCategory() {
        return category;
    }
    
    /**
     * When called, this method will subtract the appropriate amount of health from the monster.  If the monster
     * is alive, it returns true.  If the monster is dead it returns false.
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
    
    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean hasMoved) {
        moved = hasMoved;
    }

    public Room chooseMove(Room tiles) {
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
