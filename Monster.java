import javax.swing.JOptionPane;
/**
 * Abstract class Monster - This class holds all of the enemy types you might encounter during the game.  
 * This class will have the ability to check whether its alive or not, a set amount of health, a set amount of
 * attack power, and a set amount of defense.  
 * -removed abstract
 * @author Dylan, Ben
 * @version 1.2
 */
public abstract class Monster implements Tileable
{
    String name;
    char symbol;
    int health; //NO need to have doubles
    int atk;
    int x;
    int y;
    boolean moved;
    String category;
    int exp;
    public Monster() {
        
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
    
    public boolean getMoved() {
        return moved;
    }
    
    public void setMoved(boolean hasMoved) {
        moved = hasMoved;
    }
    
    public Room chooseMove(Room tiles) {
        return tiles;
    }
    
    public void setPos(int xPos, int yPos) {
        x = xPos;
        y = yPos;
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
