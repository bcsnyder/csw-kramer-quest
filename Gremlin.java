
public class Gremlin extends Monster
{
    String name;
    String symbol;
    int health; //NO need to have doubles
    int atk;
    int xPos;
    int yPos;

    public Gremlin() {
        symbol = "G";
        name = "Gremlin";
        health = (int)(Math.random()*5 + 10);
        atk = (int)(Math.random()*2 + 1);
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
    
    public int getX() {
        return xPos;
    }
    
    public int getY() {
        return yPos;
    }
    
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHP() {
        return health;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getAttack() {
        return atk;
    }
}
