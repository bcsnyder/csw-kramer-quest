
public class Axe extends Weapon
{
    private String type;
    private String name;
    private int damage;
    private int durability;
    
    public Axe() {
       type = "Weapon";
       name = "Axe";
       damage = 10;
       durability = 3;
    }
    
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int attack() {
        durability -= 1;
        return damage;
    }
    
        public int getDamage() {
        return damage;
    }
    
    public int getDurability() {
        return durability;
    }
}
