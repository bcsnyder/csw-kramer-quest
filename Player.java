import java.util.*;

public class Player
{
    private String name;
    private int health;
    private int stamina;
    private int attack;
    private int var = 0; //just a thing
    private GameOverWindow gOW = new GameOverWindow();
    
    private ArrayList<Item> inventory;
    private Weapon currentWeapon;
    
    private Room currRoom;
    private int x;
    private int y;
    private boolean inCombat;
    private boolean usedItem;
    public Player() {
        inventory = new ArrayList<Item>();
    }
    
    public int moveUp() {
        int newY = y - 1;
        
        return checkMove(x, newY);
    }
    
    public int moveDown() {
        int newY = y + 1;
        
        return checkMove(x, newY);
    }
    
    public int moveLeft() {
        int newX = x - 1;
        
        return checkMove(newX, y);
    }
    
    public int moveRight() {
        int newX = x + 1;
        
        return checkMove(newX, y);
    }
    
    private int checkMove(int xCor, int yCor) {
        if (currRoom.getTile(xCor, yCor) == '.') {
            validMove(xCor, yCor);
            return 1;
        } else if (currRoom.getTile(xCor, yCor) == '$') {
            validMove(xCor, yCor);
            return 3;
        } else if (currRoom.getTile(xCor, yCor) == '#') {
            return 2;
        } else if (currRoom.getTile(xCor, yCor) == '-' || currRoom.getTile(xCor, yCor) == '|') {
            return 0;
        } else if (currRoom.getTile(xCor, yCor) == '^') {
            return 5;
        } else {
            validMove(xCor, yCor);
            return 4;
        }
    }
    
    private void validMove(int xPos, int yPos) {
        currRoom.removePlayer();
        x = xPos;
        y = yPos;
        currRoom.addPlayer(x, y);
        stamina--;
        //checks stamina and lowers the health if stamina is too low
        if (stamina <= 0){ 
            var++;
            stamina = 0; 
            if (var == 7){
                var = 0;
                health--;
            }
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
            inventory.remove(inventory.indexOf(currentWeapon));
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
    
    public void setCombat (boolean c) {
        inCombat = c;
    }
    
    public boolean getCombat() {
        return inCombat;
    }
    
    public boolean getItemUse() {
        return usedItem;
    }
    
     public void usedItem (boolean c) {
        usedItem = c;
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
    
     public String weaponName() {
        return currentWeapon.getName();
    }
}
