import java.util.*;

public class Player implements Tileable
{
    private String name;
    private char symbol;
    private int health;
    private int stamina;
    private int attack;
    String category;
    private int var = 0; //just a thing
    private GameOverWindow gOW = new GameOverWindow();

    private ArrayList<Item> inventory;
    private Weapon currentWeapon;

    private Room currRoom;
    private int x;
    private int y;
    private boolean inCombat;
    private boolean usedItem;
    boolean cheatMode = false;
    private int maxHealth;
    private int exp;
    private SoundSystem soundtrack;
    
    public Player() {
        inventory = new ArrayList<Item>();
        symbol = '@';
        category = "Player";
    }
    
    public String getCategory() {
        return category;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public Tileable moveUp() {
        int newY = y - 1;

        return checkMove(x, newY);
    }

    public Tileable moveDown() {
        int newY = y + 1;

        return checkMove(x, newY);
    }

    public Tileable moveLeft() {
        int newX = x - 1;

        return checkMove(newX, y);
    }

    public Tileable moveRight() {
        int newX = x + 1;

        return checkMove(newX, y);
    }

    private Tileable checkMove(int xCor, int yCor) {
        if (currRoom.returnTileObject(xCor, yCor).getCategory().equals("Empty")) {
            Tileable empty = currRoom.returnTileObject(xCor, yCor);
            validMove(xCor, yCor);
            return empty;
        } else if (currRoom.returnTileObject(xCor, yCor).getCategory().equals("Item")) {
            Tileable itemFound = currRoom.returnTileObject(xCor, yCor);
            validMove(xCor, yCor);
            return itemFound;
        } else {
            return currRoom.returnTileObject(xCor, yCor);
        }
    }

    private void validMove(int xPos, int yPos) {
        Player play = (Player)currRoom.removeTile(x,y);
        x = xPos;
        y = yPos;
        currRoom.addTile(x, y, play);
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
    
    public void setSoundtrack(SoundSystem s) {
        soundtrack = s;
    }
    
    public SoundSystem getSoundtrack() {
        return soundtrack;
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
        int damage = attack + currentWeapon.attack() + (exp/8);
        if (currentWeapon.getDurability() <= 0) {
            inventory.remove(inventory.indexOf(currentWeapon));
            currentWeapon = new Fists();
        }

        return damage;
    }

    public int getAttack() {
        return (attack + currentWeapon.getDamage() + (exp/8));
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
    
    public void enableCheats() {
        cheatMode = true;
    }
    
    public boolean cheatCheck() {
        return cheatMode;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
     public void maxHealthAdd(int x) {
        maxHealth = maxHealth + x;
    }
    
    public void setMaxHealth(int x) {
        maxHealth = x;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    
    public void setExp (int x) {
        exp = x;
    }
    
    public void addExp (int x) {
        exp = exp + x;
    }
}
