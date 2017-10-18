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
    
    public int moveUp() {
        int newY = y - 1;
        
        if(currRoom.getTile(x, newY) == '.') {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 1;
        } else if(currRoom.getTile(x, newY) == '$') {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 3;
        } else if(currRoom.getTile(x, newY) == '#') {
            return 2;
        } else if(currRoom.getTile(x, newY) == '-' || currRoom.getTile(x, newY) == '|') {
            return 0;
        } else {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 4;
        }
    }
    
    public int moveDown() {
        int newY = y + 1;
        
        if(currRoom.getTile(x, newY) == '.') {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 1;
        } else if(currRoom.getTile(x, newY) == '$') {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 3;
        } else if(currRoom.getTile(x, newY) == '#') {
            return 2;
        } else if(currRoom.getTile(x, newY) == '-' || currRoom.getTile(x, newY) == '|') {
            return 0;
        } else {
            currRoom.removePlayer();
            y = newY;
            currRoom.addPlayer(x, y);
            return 4;
        }
    }
    
    public int moveLeft() {
        int newX = x - 1;
        
        if(currRoom.getTile(newX, y) == '.') {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 1;
        } else if(currRoom.getTile(newX, y) == '$') {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 3;
        } else if(currRoom.getTile(newX, y) == '#') {
            return 2;
        } else if(currRoom.getTile(newX, y) == '-' || currRoom.getTile(newX, y) == '|') {
            return 0;
        } else {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 4;
        }
    }
    
    public int moveRight() {
        int newX = x + 1;
        
        if(currRoom.getTile(newX, y) == '.') {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 1;
        } else if(currRoom.getTile(newX, y) == '$') {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 3;
        } else if(currRoom.getTile(newX, y) == '#') {
            return 2;
        } else if(currRoom.getTile(newX, y) == '-' || currRoom.getTile(newX, y) == '|') {
            return 0;
        } else {
            currRoom.removePlayer();
            x = newX;
            currRoom.addPlayer(x, y);
            return 4;
        }
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getStamina() {
        return stamina;
    }
    
    public void setStamina(int s) {
        stamina = s;
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
    
    public void addItem(Item newItem) {
        inventory.add(newItem);
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
    
    public Room getRoom() {
        return currRoom;
    }
    
    public void setPos(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }
    
    public void setWeapon(Weapon w) {
        currentWeapon = w;
    }
    
    public ArrayList getInventory() {
        return inventory;
    }
}
