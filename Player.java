import java.util.*;

public class Player
{
    private String name;
    private int health;
    private int stamina;
    private int attack;
    
    private ArrayList<Item> inventory;
    private Weapon currentWeapon;
    
    private Room currRoom;
    private int x;
    private int y;
    
    public Player() {
        inventory = new ArrayList<Item>();
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getStamina() {
        return stamina;
    }
    
    public int getDur() {
        return currentWeapon.getDurability();
    }
    
    public int attack() {
        int damage = attack + currentWeapon.attack();
        
        if (currentWeapon.getDurability() <= 0) {
            currentWeapon = new Fists();
        }
        
        return damage;
    }
    
    public int getAttack() {
        return (attack + currentWeapon.getDamage());
    }
    
    public void setName(String pName) {
        name = pName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setHealth(int hP) {
        health = hP;
    }
    
    public void setAttack(int atk) {
        attack = atk;
    }
    
    public void setRoom(Room loc) {
        currRoom = loc;
    }
    
    public void setPos(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }
    
    public void setWeapon(Weapon w) {
        currentWeapon = w;
    }
}
