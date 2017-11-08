public class Changeling extends Monster implements Tileable
{
    String name;
    char symbol;
    int health; //NO need to have doubles
    int atk;

    public Changeling() {
        symbol = '?';
        name = "Changeling";
        health = (int)(Math.random()*101);
        atk = (int)(Math.random()*31);
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