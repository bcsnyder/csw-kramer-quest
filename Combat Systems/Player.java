import javax.swing.JOptionPane;
/**
 * Abstract class Player - This class serves as the player's body and will monitor several important things.  
 * This class will have the ability to check whether its alive or not, a set amount of health, a set amount of
 * attack power plus or minus equpiment, and a set amount of defense.  
 * 
 * @author Dylan
 * @version 1.1
 */
public class Player
{
    private boolean alive;
    double health = 100;
    double atk = Integer.parseInt(JOptionPane.showInputDialog("Player's atk:")); //+ Weapon.damage();
    private double def = 1;    

    /**
     * When called, this method will subtract the appropriate amount of health from the monster.  If the monster
     * is alive, it returns 1.  If the monster is dead it returns 0
     * 
     */
    public boolean ouchie(double y)
    {
        health = health - y;
        if (health <= 0){
            return false;
        }else{
            return true;
        }
    }
}


