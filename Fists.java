public class Fists extends Weapon
{
    private char symbol;
    private String type;
    private String name;
    private int damage;
    private int durability;
    
    public Fists() {
       type = "Weapon";
       name = "Fists";
       damage = 0;
       durability = 1;
       symbol = '$';
    }
    
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int attack() {
        durability -= 0;
        return damage;
    }
    
        public int getDamage() {
        return damage;
    }
    
    public int getDurability() {
        return durability;
    }
    
    public char getSymbol() {
        return symbol;
    }
}
