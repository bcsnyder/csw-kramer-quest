
public class Excalibur extends Weapon
{
    private String type;
    private String name;
    private int damage;
    private int durability;
    
    public Excalibur() {
       type = "Weapon";
       name = "Excalibur";
       damage = 999;
       durability = 999;
    }
    
    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int attack() {
        return damage;
    }
    
        public int getDamage() {
        return damage;
    }
    
    public int getDurability() {
        return durability;
    }
}
