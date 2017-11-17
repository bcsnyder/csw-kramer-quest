
public class Excalibur extends Weapon
{
    private String type;
    private String name;
    private int damage;
    private int durability;
    private String category;
    
    public Excalibur() {
       type = "Weapon";
       category = "Item";
       name = "Excalibur";
       damage = 997;
       durability = 999;
    }
    
    public String getCategory() {
        return category;
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
