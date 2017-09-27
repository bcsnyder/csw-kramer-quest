
import java.util.*;
/**
 * Write a description of class TheRing here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TheRing
{
    public static void main (String args []){
       
        Monster enemy = new Monster();
        Player player = new Player();
        double playerAtk = player.atk;
        double enemyAtk = enemy.atk;
        
        boolean i = true;
        while(i){
            if(enemy.ouchie(playerAtk) == false){
                System.out.println("Oof\nMonster HP: 0");
                i = false;
                System.out.println("You killed the monster.");
                break;
            }else{
                System.out.println("Oof\nMonster HP: " + Math.ceil(enemy.health));
            }
            
            if(player.ouchie(enemy.atk) == false){
                System.out.println("Ouch!\nPlayer HP: 0");
                i = false;
                System.out.println("You died... Game over.");
            }else{
                System.out.println("Ouch!\nPlayer HP: " + Math.ceil(player.health));
            }
        }
    }
}
